package tech.henskens.maintenanceservice.mapper;

import org.springframework.stereotype.Component;
import tech.henskens.maintenanceservice.dto.maintenance.MaintenanceDto;
import tech.henskens.maintenanceservice.dto.maintenance.MaintenanceWithStationAndSessionsDto;
import tech.henskens.maintenanceservice.dto.station.StationDto;
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

    public Maintenance toMaintenance(MaintenanceDto dto) {
        Maintenance maintenance = new Maintenance();
        maintenance.setId(dto.getId());
        maintenance.setStationIdentifier(dto.getStationIdentifier());
        maintenance.setIssueCategory(dto.getIssueCategory());
        maintenance.setIssueDescription(dto.getIssueDescription());
        maintenance.setCreationDate(dto.getCreationDate());
        maintenance.setMaintenanceDate(dto.getMaintenanceDate());
        maintenance.setStatus(dto.getStatus());
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
        dto.setCreationDate(maintenance.getCreationDate());
        dto.setStation(station);
        dto.setTotalCompletedSessions(totalCompletedSessions);
        return dto;
    }
}

