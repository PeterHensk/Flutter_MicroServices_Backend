package tech.henskens.sessionservice.manager.session;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import tech.henskens.sessionservice.dto.session.DateRangeDto;
import tech.henskens.sessionservice.dto.session.SessionDto;
import tech.henskens.sessionservice.dto.station.ChargingPortDto;
import tech.henskens.sessionservice.manager.station.IStationManager;
import tech.henskens.sessionservice.mapper.session.SessionMapper;
import tech.henskens.sessionservice.model.Car;
import tech.henskens.sessionservice.model.Session;
import tech.henskens.sessionservice.model.User;
import tech.henskens.sessionservice.repository.ICarRepository;
import tech.henskens.sessionservice.repository.ISessionRepository;
import tech.henskens.sessionservice.repository.IUserRepository;

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

    public SessionDto createSession(SessionDto sessionDto) {
        String portStatus = this.checkPortAvailability(sessionDto);
        if (!"AVAILABLE".equals(portStatus)) {
            throw new IllegalArgumentException("Port is not available for charging.");
        } else {
            Car car = this.carRepository.findById(sessionDto.getCarId()).orElseThrow(() -> new IllegalArgumentException("Car not found"));
            User user = this.userRepository.findById(sessionDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Session session = this.sessionMapper.toSession(sessionDto);
            session.setCar(car);
            session.setUser(user);
            session = this.sessionRepository.save(session);
            return this.sessionMapper.toSessionDto(session);
        }
    }

    public SessionDto updateSession(Long id, SessionDto sessionDto) {
        Session session = this.sessionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Session not found."));
        if (sessionDto.getCarId() != null) {
            Car car = this.carRepository.findById(sessionDto.getCarId()).orElseThrow(() -> new IllegalArgumentException("Car not found"));
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

    private String checkPortAvailability(SessionDto sessionDto) {
        Optional<ChargingPortDto> chargingPortOptional = this.stationManager.getChargingPort(sessionDto.getStationIdentifier(), sessionDto.getPortIdentifier());
        ChargingPortDto chargingPort = chargingPortOptional.orElseThrow(() -> new NoSuchElementException("Charging port not found with id: " + sessionDto.getPortIdentifier()));
        return chargingPort.getStatus();
    }
}

