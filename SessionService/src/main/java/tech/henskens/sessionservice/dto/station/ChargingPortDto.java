package tech.henskens.sessionservice.dto.station;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChargingPortDto {
    private String portIdentifier;
    private String status;
}
