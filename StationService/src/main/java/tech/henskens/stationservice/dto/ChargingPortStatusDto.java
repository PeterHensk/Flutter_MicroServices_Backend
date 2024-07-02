package tech.henskens.stationservice.dto;

import lombok.Getter;
import lombok.Setter;
import tech.henskens.stationservice.model.ChargingPortStatus;

@Setter
@Getter
public class ChargingPortStatusDto {
    private ChargingPortStatus status;
}

