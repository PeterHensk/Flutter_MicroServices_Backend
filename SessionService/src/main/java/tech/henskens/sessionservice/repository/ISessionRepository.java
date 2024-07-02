package tech.henskens.sessionservice.repository;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.henskens.sessionservice.model.Session;

public interface ISessionRepository extends JpaRepository<Session, Long> {
    int countByStartedBetweenAndStationIdentifier(LocalDateTime startDate, LocalDateTime endDate, String stationIdentifier);
}
