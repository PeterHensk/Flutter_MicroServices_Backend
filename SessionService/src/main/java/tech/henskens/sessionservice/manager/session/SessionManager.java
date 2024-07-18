package tech.henskens.sessionservice.manager.session;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.henskens.sessionservice.dto.session.CreateSessionDto;
import tech.henskens.sessionservice.dto.session.DateRangeDto;
import tech.henskens.sessionservice.dto.session.SessionDto;
import tech.henskens.sessionservice.dto.session.StartSessionDto;
import tech.henskens.sessionservice.dto.station.ChargingPortDto;
import tech.henskens.sessionservice.manager.station.IStationManager;
import tech.henskens.sessionservice.mapper.session.SessionMapper;
import tech.henskens.sessionservice.model.Car;
import tech.henskens.sessionservice.model.Session;
import tech.henskens.sessionservice.model.User;
import tech.henskens.sessionservice.repository.ICarRepository;
import tech.henskens.sessionservice.repository.ISessionRepository;
import tech.henskens.sessionservice.repository.IUserRepository;

import java.time.LocalDateTime;

@Service
public class SessionManager implements ISessionManager {
    private final ISessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private final IStationManager stationManager;
    private final ICarRepository carRepository;
    private final IUserRepository userRepository;

    public SessionManager(ISessionRepository sessionRepository, SessionMapper sessionMapper, IStationManager stationManager, ICarRepository carRepository, IUserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.sessionMapper = sessionMapper;
        this.stationManager = stationManager;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    public SessionDto createSession(CreateSessionDto createSessionDto) {
        String portStatus = this.checkPortAvailability(createSessionDto.getStationIdentifier(), createSessionDto.getPortIdentifier());
        if (!userRepository.existsById(createSessionDto.getUserId())) {
            throw new NoSuchElementException("User with id " + createSessionDto.getUserId() + " does not exist");
        }
        if (!carRepository.existsById(createSessionDto.getCarId())) {
            throw new NoSuchElementException("Car with id " + createSessionDto.getUserId() + " does not exist");
        }
        if (!"AVAILABLE".equals(portStatus)) {
            throw new IllegalArgumentException("Port is not available for charging.");
        } else {
            Car car = this.carRepository.findById(createSessionDto.getCarId()).orElseThrow(() -> new IllegalArgumentException("Car not found"));
            User user = this.userRepository.findById(createSessionDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Session session = this.sessionMapper.toSession(createSessionDto);
            session.setCar(car);
            session.setUser(user);
            session = this.sessionRepository.save(session);
            return this.sessionMapper.toSessionDto(session);
        }
    }

    public SessionDto updateSession(Long id, SessionDto sessionDto) {
        Session session = this.sessionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Session not found."));
        if (sessionDto.getCar().getId() != null) {
            Car car = this.carRepository.findById(sessionDto.getCar().getId()).orElseThrow(() -> new IllegalArgumentException("Car not found"));
            session.setCar(car);
        }

        if (sessionDto.getUserId() != null) {
            User user = this.userRepository.findById(sessionDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            session.setUser(user);
        }

        this.sessionMapper.updateSessionFromDto(sessionDto, session);
        session = this.sessionRepository.save(session);
        return this.sessionMapper.toSessionDto(session);
    }

    public int countSessionsBetweenDates(DateRangeDto dateRangeDto) {
        return this.sessionRepository.countByStartedBetweenAndStationIdentifier(dateRangeDto.getStartDate(), dateRangeDto.getEndDate(), dateRangeDto.getStationIdentifier());
    }

    @Override
    public Page<SessionDto> getAllSessions(Pageable pageable) {
        return sessionRepository.findAll(pageable).map(sessionMapper::toSessionDto);
    }

    @Override
    public void deleteById(Long id) {
        if (!sessionRepository.existsById(id)) {
            throw new NoSuchElementException("Session with id " + id + " does not exist");
        }
        sessionRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<SessionDto> startSession(User authenticatedUser, StartSessionDto startSessionDto) {
        User existingUser = this.userRepository.findById(authenticatedUser.getId())
                .orElseThrow(() -> new NoSuchElementException("User with id " + authenticatedUser.getId() + " does not exist"));
        Car existingCar = this.carRepository.findByLicensePlate(startSessionDto.getLicensePlate())
                .orElseThrow(() -> new NoSuchElementException("Car with license plate " + startSessionDto.getLicensePlate() + " does not exist"));

        String portStatus = this.checkPortAvailability(startSessionDto.getStationIdentifier(), startSessionDto.getPortIdentifier());
        if (!"AVAILABLE".equals(portStatus)) {
            throw new IllegalArgumentException("Port is not available for charging.");
        }
        this.stationManager.updateChargingPortStatus(startSessionDto.getStationIdentifier(), startSessionDto.getPortIdentifier(), "IN_USE");

        Session session = sessionMapper.toSessionFromStartSessionDto(startSessionDto, existingUser, existingCar);
        session = this.sessionRepository.save(session);
        SessionDto sessionDto = this.sessionMapper.toSessionDto(session);
        if (sessionDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(sessionDto, HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<SessionDto> stopSession(Long id) {
        Session session = this.sessionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Session with id " + id + " does not exist"));

        LocalDateTime now = LocalDateTime.now();
        session.setEnded(now);
        long seconds = Duration.between(session.getStarted(), now).getSeconds();
        session.setKwh(seconds * 0.002);

        this.stationManager.updateChargingPortStatus(session.getStationIdentifier(), session.getPortIdentifier(), "AVAILABLE");

        session = this.sessionRepository.save(session);
        SessionDto sessionDto = this.sessionMapper.toSessionDto(session);
        if (sessionDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(sessionDto, HttpStatus.OK);
        }
    }

    private String checkPortAvailability(String stationIdentifier, String portIdentifier) {
        Optional<ChargingPortDto> chargingPortOptional = this.stationManager.getChargingPort(stationIdentifier, portIdentifier);
        ChargingPortDto chargingPort = chargingPortOptional.orElseThrow(() -> new NoSuchElementException("Charging port not found with id: " + portIdentifier));
        return chargingPort.getStatus();
    }

    @Override
    public ResponseEntity<SessionDto> getSessionForAuthenticatedUser(User user) {
        Session session = sessionRepository.findByUserAndEndedIsNull(user);
        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            SessionDto sessionDto = sessionMapper.toSessionDto(session);
            return new ResponseEntity<>(sessionDto, HttpStatus.OK);
        }
    }
}

