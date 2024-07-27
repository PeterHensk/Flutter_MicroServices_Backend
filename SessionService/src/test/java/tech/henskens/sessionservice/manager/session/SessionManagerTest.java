package tech.henskens.sessionservice.manager.session;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import tech.henskens.sessionservice.dto.session.DateRangeDto;
import tech.henskens.sessionservice.dto.session.SessionDto;
import tech.henskens.sessionservice.dto.session.StartSessionDto;
import tech.henskens.sessionservice.dto.station.ChargingPortDto;
import tech.henskens.sessionservice.manager.station.StationManager;
import tech.henskens.sessionservice.mapper.session.SessionMapper;
import tech.henskens.sessionservice.model.Car;
import tech.henskens.sessionservice.dto.car.CarDto;
import tech.henskens.sessionservice.model.Session;
import tech.henskens.sessionservice.model.User;
import tech.henskens.sessionservice.repository.ICarRepository;
import tech.henskens.sessionservice.repository.ISessionRepository;
import tech.henskens.sessionservice.repository.IUserRepository;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionManagerTest {

    @Mock
    private ISessionRepository sessionRepository;

    @Mock
    private ICarRepository carRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private SessionMapper sessionMapper;

    @Mock
    private StationManager stationManager;

    @InjectMocks
    private SessionManager sessionManager;

    @Test
    public void testUpdateSession() {
        Long id = 1L;
        SessionDto sessionDto = new SessionDto();
        sessionDto.setUserId(1L);
        CarDto carDto = new CarDto();
        carDto.setId(1L);
        sessionDto.setCar(carDto);

        Session session = new Session();
        Car car = new Car();
        User user = new User();

        when(sessionRepository.findById(id)).thenReturn(Optional.of(session));
        when(carRepository.findById(carDto.getId())).thenReturn(Optional.of(car));
        when(userRepository.findById(sessionDto.getUserId())).thenReturn(Optional.of(user));
        when(sessionRepository.save(any(Session.class))).thenAnswer(i -> i.getArguments()[0]);
        when(sessionMapper.toSessionDto(any(Session.class))).thenAnswer(i -> sessionDto);

        SessionDto result = sessionManager.updateSession(id, sessionDto);

        assertEquals(sessionDto, result);
    }

    @Test
    public void testCountSessionsBetweenDates() {
        DateRangeDto dateRangeDto = new DateRangeDto();
        dateRangeDto.setStartDate(LocalDateTime.now().minusDays(1));
        dateRangeDto.setEndDate(LocalDateTime.now());
        dateRangeDto.setStationIdentifier("station1");

        int expectedCount = 5;
        when(sessionRepository.countByStartedBetweenAndStationIdentifier(dateRangeDto.getStartDate(), dateRangeDto.getEndDate(), dateRangeDto.getStationIdentifier())).thenReturn(expectedCount);

        int result = sessionManager.countSessionsBetweenDates(dateRangeDto);

        assertEquals(expectedCount, result);
    }

    @Test
    public void testGetAllSessions() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Session> sessions = new ArrayList<>();
        sessions.add(new Session());
        sessions.add(new Session());
        sessions.add(new Session());
        Page<Session> page = new PageImpl<>(sessions);
        when(sessionRepository.findAll(pageable)).thenReturn(page);
        Page<SessionDto> result = sessionManager.getAllSessions(pageable);
        assertEquals(sessions.size(), result.getContent().size());
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;
        when(sessionRepository.existsById(id)).thenReturn(true);
        doNothing().when(sessionRepository).deleteById(id);
        sessionManager.deleteById(id);
        verify(sessionRepository).deleteById(id);
    }

    @Test
    public void testStartSession() {
        // Given
        String token = "token";
        User authenticatedUser = new User();
        authenticatedUser.setId(1L);
        StartSessionDto startSessionDto = new StartSessionDto();
        startSessionDto.setLicensePlate("ABC-123");
        startSessionDto.setStationIdentifier("station1");
        startSessionDto.setPortIdentifier("1");

        User existingUser = new User();
        Car existingCar = new Car();
        Session session = new Session();
        SessionDto sessionDto = new SessionDto();

        ChargingPortDto chargingPortDto = new ChargingPortDto();
        chargingPortDto.setStatus("AVAILABLE");

        // Set up mocks
        when(stationManager.getChargingPort(token, startSessionDto.getStationIdentifier(), startSessionDto.getPortIdentifier())).thenReturn(Optional.of(chargingPortDto));
        when(userRepository.findById(authenticatedUser.getId())).thenReturn(Optional.of(existingUser));
        when(carRepository.findByLicensePlate(startSessionDto.getLicensePlate())).thenReturn(Optional.of(existingCar));
        when(sessionMapper.toSessionFromStartSessionDto(startSessionDto, existingUser, existingCar)).thenReturn(session);
        when(sessionRepository.save(session)).thenReturn(session);
        when(sessionMapper.toSessionDto(session)).thenReturn(sessionDto);

        // When
        ResponseEntity<SessionDto> response = sessionManager.startSession(token, authenticatedUser, startSessionDto);

        // Then
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sessionDto, response.getBody());
    }

    @Test
    public void testStopSession() {
        String token = "token";
        Long id = 1L;
        Session session = new Session();
        session.setStarted(LocalDateTime.now().minusHours(1));

        when(sessionRepository.findById(id)).thenReturn(Optional.of(session));
        when(sessionRepository.save(session)).thenReturn(session);
        SessionDto sessionDto = new SessionDto();

        when(sessionMapper.toSessionDto(any(Session.class))).thenReturn(sessionDto);
        ResponseEntity<SessionDto> result = sessionManager.stopSession(token, id);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    public void testStopSession_NoContent() {
        String token = "token";
        Long id = 1L;

        Session session = new Session();
        session.setStarted(LocalDateTime.now().minusHours(1));

        when(sessionRepository.findById(id)).thenReturn(Optional.of(session));
        when(sessionRepository.save(session)).thenReturn(session);
        when(sessionMapper.toSessionDto(any(Session.class))).thenReturn(null);

        ResponseEntity<SessionDto> result = sessionManager.stopSession(token, id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    public void testGetSessionForAuthenticatedUser_SessionFound() {
        User user = new User();
        user.setId(1L);
        Session session = new Session();
        session.setId(1L);
        session.setUser(user);
        session.setStarted(LocalDateTime.now().minusHours(1));
        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(1L);
        when(sessionRepository.findByUserAndEndedIsNull(user)).thenReturn(session);
        when(sessionMapper.toSessionDto(session)).thenReturn(sessionDto);
        ResponseEntity<SessionDto> response = sessionManager.getSessionForAuthenticatedUser(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sessionDto, response.getBody());
    }

    @Test
    public void testGetSessionForAuthenticatedUser_NoSessionFound() {
        User user = new User();
        user.setId(1L);
        when(sessionRepository.findByUserAndEndedIsNull(user)).thenReturn(null);
        ResponseEntity<SessionDto> response = sessionManager.getSessionForAuthenticatedUser(user);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}
