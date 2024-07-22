package tech.henskens.maintenanceservice.manager.session;

import java.time.LocalDateTime;
import java.util.Optional;

import tech.henskens.maintenanceservice.dto.session.SessionCountDto;

public interface ISessionManager {
    Optional<SessionCountDto> getSessionCount(String token, String stationIdentifier, LocalDateTime startDate, LocalDateTime endDate);
}
