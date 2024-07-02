package tech.henskens.stationservice.model;

import java.util.List;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Station {
    public String stationIdentifier;
    public Location location;
    public List<ChargingPort> chargingPorts;
    public String imageId;
}
