package tech.henskens.maintenanceservice.dto.maintenance;

import java.time.LocalDateTime;
import tech.henskens.maintenanceservice.dto.station.StationDto;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceWithStationAndSessionsDto {
    private LocalDateTime creationDate;
    private StationDto station;
    private Integer totalCompletedSessions;
}

