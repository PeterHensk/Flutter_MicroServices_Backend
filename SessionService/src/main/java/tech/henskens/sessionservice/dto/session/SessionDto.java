package tech.henskens.sessionservice.dto.session;

import lombok.Getter;
import lombok.Setter;
import tech.henskens.sessionservice.dto.car.CarDto;
import tech.henskens.sessionservice.model.Car;

import java.time.LocalDateTime;

@Setter
@Getter
public class SessionDto {
    private CarDto car;
    private Long userId;
    private String stationIdentifier;
    private String portIdentifier;
    private Double kwh;
    private LocalDateTime started;
    private LocalDateTime ended;
}

