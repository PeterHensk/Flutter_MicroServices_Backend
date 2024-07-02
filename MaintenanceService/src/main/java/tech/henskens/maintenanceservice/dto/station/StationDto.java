package tech.henskens.maintenanceservice.dto.station;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StationDto {
    public String stationIdentifier;
    public Location location;
}
