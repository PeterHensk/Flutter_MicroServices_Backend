package tech.henskens.sessionservice.dto.session;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class SessionDto {
    private Long id;
    private Long carId;
    private Long userId;
    private String stationIdentifier;
    private String portIdentifier;
    private Double kwh;
    private LocalDateTime started;
    private LocalDateTime ended;
}

