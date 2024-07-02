package tech.henskens.stationservice.dto;

import lombok.*;
import tech.henskens.stationservice.model.ChargingPortStatus;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargingPortDto {
    private String portIdentifier;
    private ChargingPortStatus status;
}

