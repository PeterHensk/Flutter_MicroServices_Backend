package tech.henskens.maintenanceservice.manager.maintenance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.henskens.maintenanceservice.dto.maintenance.MaintenanceDto;
import tech.henskens.maintenanceservice.dto.maintenance.MaintenanceWithStationAndSessionsDto;

public interface IMaintenanceManager {
    MaintenanceDto createMaintenance(String token, MaintenanceDto maintenanceDto);

    MaintenanceDto updateMaintenance(String token, MaintenanceDto maintenanceDto);

    Page<MaintenanceDto> getAllMaintenances(Pageable pageable);

    void deleteMaintenance(Long id);

    Page<MaintenanceWithStationAndSessionsDto> getAllMaintenancesWithStationAndSessions(String token, Pageable pageable);
}
