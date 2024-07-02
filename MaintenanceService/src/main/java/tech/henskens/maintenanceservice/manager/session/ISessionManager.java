package tech.henskens.maintenanceservice.manager.session;

import java.time.LocalDateTime;
import tech.henskens.maintenanceservice.dto.session.SessionCountDto;

public interface ISessionManager {
    SessionCountDto getSessionCount(String stationIdentifier, LocalDateTime startDate, LocalDateTime endDate);
}
