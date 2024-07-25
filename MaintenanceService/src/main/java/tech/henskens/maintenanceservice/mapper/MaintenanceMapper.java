package tech.henskens.maintenanceservice.mapper;

import org.springframework.stereotype.Component;
import tech.henskens.maintenanceservice.dto.maintenance.MaintenanceDto;
import tech.henskens.maintenanceservice.dto.maintenance.MaintenanceWithStationAndSessionsDto;
import tech.henskens.maintenanceservice.dto.station.StationDto;
import tech.henskens.maintenanceservice.dto.station.ChargingPortStatusDto;
import tech.henskens.maintenanceservice.model.Maintenance;

@Component
public class MaintenanceMapper {
    public MaintenanceMapper() {
    }

    public MaintenanceDto toMaintenanceDto(Maintenance maintenance) {
        MaintenanceDto dto = new MaintenanceDto();
        dto.setId(maintenance.getId());
        dto.setStationIdentifier(maintenance.getStationIdentifier());
        dto.setIssueCategory(maintenance.getIssueCategory());
        dto.setIssueDescription(maintenance.getIssueDescription());
        dto.setCreationDate(maintenance.getCreationDate());
        dto.setMaintenanceDate(maintenance.getMaintenanceDate());
        dto.setStatus(maintenance.getStatus());
        return dto;
    }

    public Maintenance toMaintenance(MaintenanceDto maintenanceDto) {
        Maintenance maintenance = new Maintenance();
        maintenance.setId(maintenanceDto.getId());
        maintenance.setStationIdentifier(maintenanceDto.getStationIdentifier());
        maintenance.setIssueCategory(maintenanceDto.getIssueCategory());
        maintenance.setIssueDescription(maintenanceDto.getIssueDescription());
        maintenance.setCreationDate(maintenanceDto.getCreationDate());
        maintenance.setMaintenanceDate(maintenanceDto.getMaintenanceDate());
        maintenance.setStatus(maintenanceDto.getStatus());
        return maintenance;
    }

    public void updateMaintenance(Maintenance existingMaintenance, MaintenanceDto maintenanceDto) {
        if (maintenanceDto.getIssueCategory() != null) {
            existingMaintenance.setIssueCategory(maintenanceDto.getIssueCategory());
        }

        if (maintenanceDto.getIssueDescription() != null) {
            existingMaintenance.setIssueDescription(maintenanceDto.getIssueDescription());
        }

        if (maintenanceDto.getMaintenanceDate() != null) {
            existingMaintenance.setMaintenanceDate(maintenanceDto.getMaintenanceDate());
        }

        if (maintenanceDto.getStatus() != null) {
            existingMaintenance.setStatus(maintenanceDto.getStatus());
        }

    }

    public MaintenanceWithStationAndSessionsDto toMaintenanceWithStationAndSessionsDto(Maintenance maintenance, StationDto station, Integer totalCompletedSessions) {
        MaintenanceWithStationAndSessionsDto dto = new MaintenanceWithStationAndSessionsDto();
        dto.setId(maintenance.getId());
        dto.setCreationDate(maintenance.getCreationDate());
        dto.setMaintenanceDate(maintenance.getMaintenanceDate());
        dto.setStatus(maintenance.getStatus());
        dto.setIssueCategory(maintenance.getIssueCategory());
        dto.setIssueDescription(maintenance.getIssueDescription());
        dto.setStation(station);
        dto.setTotalCompletedSessions(totalCompletedSessions);
        return dto;
    }

    public static ChargingPortStatusDto toChargingPortStatusDtoFromSession(Maintenance maintenance, String status) {
        ChargingPortStatusDto dto = new ChargingPortStatusDto();
        dto.setStationIdentifier(maintenance.getStationIdentifier());
        dto.setStatus(status);
        return dto;
    }
}

