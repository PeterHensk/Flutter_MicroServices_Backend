package tech.henskens.maintenanceservice.repository;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.henskens.maintenanceservice.model.Maintenance;

public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {
    Maintenance findFirstByStationIdentifierAndCreationDateAfterOrderByCreationDate(String stationIdentifier, LocalDateTime date);
}