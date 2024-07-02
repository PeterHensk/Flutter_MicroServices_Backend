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
public class Location {
    @Id
    private String locationId;
    private String country;
    private String city;
    private String postalCode;
    private String addressLine1;
    private String parkingName;
}

