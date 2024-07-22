package tech.henskens.maintenanceservice.dto.station;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargingPortStatusDto {
    private String stationIdentifier;
    private String portIdentifier;
    private String status;
}
