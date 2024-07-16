package tech.henskens.sessionservice.manager.session;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.henskens.sessionservice.dto.session.CreateSessionDto;
import tech.henskens.sessionservice.dto.session.DateRangeDto;
import tech.henskens.sessionservice.dto.session.SessionDto;
import tech.henskens.sessionservice.dto.session.StartSessionDto;
import tech.henskens.sessionservice.model.User;

public interface ISessionManager {
    SessionDto createSession(CreateSessionDto createSessionDto);

    SessionDto updateSession(Long id, SessionDto sessionDto);

    int countSessionsBetweenDates(DateRangeDto dateRangeDto);

    Page<SessionDto> getAllSessions(Pageable pageable);

    void deleteById(Long id);

    SessionDto startSession(User user, StartSessionDto startSessionDto);

    SessionDto stopSession(Long id);
}