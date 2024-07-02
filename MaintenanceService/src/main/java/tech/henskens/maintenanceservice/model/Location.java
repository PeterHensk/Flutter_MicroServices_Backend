package tech.henskens.maintenanceservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private String locationId;
    private String country;
    private String city;
    private String postalCode;
    private String addressLine1;
    private String parkingName;
}
