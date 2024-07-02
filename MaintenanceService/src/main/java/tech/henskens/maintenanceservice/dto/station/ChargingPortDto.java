package tech.henskens.maintenanceservice.dto.station;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargingPortDto {
    private String portIdentifier;
    private String status;
}
