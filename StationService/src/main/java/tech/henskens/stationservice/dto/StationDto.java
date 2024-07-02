package tech.henskens.stationservice.dto;

import java.util.List;

import lombok.*;
import tech.henskens.stationservice.model.ChargingPort;
import tech.henskens.stationservice.model.Location;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StationDto {
    private String stationIdentifier;
    private Location location;
    private List<ChargingPort> chargingPorts;
    private String imageId;
    private String image;
    private String contentType;
}

