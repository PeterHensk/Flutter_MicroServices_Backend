package tech.henskens.sessionservice.dto.session;

import lombok.Getter;
import lombok.Setter;
import tech.henskens.sessionservice.model.Car;
import tech.henskens.sessionservice.model.User;

import java.time.LocalDateTime;

@Setter
@Getter
public class StartSessionDto {
    private String licensePlate;
    private String stationIdentifier;
    private String portIdentifier;
}
