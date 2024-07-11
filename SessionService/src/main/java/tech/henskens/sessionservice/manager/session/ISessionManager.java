package tech.henskens.sessionservice.manager.session;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.henskens.sessionservice.dto.session.DateRangeDto;
import tech.henskens.sessionservice.dto.session.SessionDto;

public interface ISessionManager {
    SessionDto createSession(SessionDto sessionDto);

    SessionDto updateSession(Long id, SessionDto sessionDto);

    int countSessionsBetweenDates(DateRangeDto dateRangeDto);

    Page<SessionDto> getAllSessions(Pageable pageable);

    void deleteById(Long id);
}