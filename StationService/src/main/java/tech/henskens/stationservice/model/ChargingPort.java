package tech.henskens.stationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChargingPort {
    @Id
    private String chargingPortId;
    private String portIdentifier;
    private ChargingPortStatus status;
}

