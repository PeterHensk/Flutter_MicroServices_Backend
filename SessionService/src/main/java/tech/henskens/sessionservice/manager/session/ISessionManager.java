package tech.henskens.sessionservice.manager.session;

import org.antlr.v4.runtime.Token;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import tech.henskens.sessionservice.dto.session.CreateSessionDto;
import tech.henskens.sessionservice.dto.session.DateRangeDto;
import tech.henskens.sessionservice.dto.session.SessionDto;
import tech.henskens.sessionservice.dto.session.StartSessionDto;
import tech.henskens.sessionservice.model.Session;
import tech.henskens.sessionservice.model.User;

public interface ISessionManager {

    SessionDto updateSession(Long id, SessionDto sessionDto);

    int countSessionsBetweenDates(DateRangeDto dateRangeDto);

    Page<SessionDto> getAllSessions(Pageable pageable);

    void deleteById(Long id);

    ResponseEntity<SessionDto> startSession(String token, User authenticatedUser, StartSessionDto startSessionDto);

    ResponseEntity<SessionDto> stopSession(String token, Long id);

    ResponseEntity<SessionDto> getSessionForAuthenticatedUser(User user);
}