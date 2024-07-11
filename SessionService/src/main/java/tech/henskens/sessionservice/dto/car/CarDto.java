package tech.henskens.sessionservice.dto.car;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CarDto {
    private Long id;
    private String brand;
    private String model;
    private String licensePlate;
}

