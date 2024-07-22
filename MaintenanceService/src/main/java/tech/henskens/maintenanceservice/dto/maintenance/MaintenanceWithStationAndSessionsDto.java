package tech.henskens.maintenanceservice.dto.maintenance;

import java.time.LocalDateTime;
import tech.henskens.maintenanceservice.dto.station.StationDto;
import lombok.*;
import tech.henskens.maintenanceservice.model.IssueCategory;
import tech.henskens.maintenanceservice.model.Station;
import tech.henskens.maintenanceservice.model.Status;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceWithStationAndSessionsDto {
    private LocalDateTime creationDate;
    private LocalDateTime maintenanceDate;
    private Status status;
    private String issueDescription;
    private IssueCategory issueCategory;
    private StationDto station;
    private Integer totalCompletedSessions;
}

