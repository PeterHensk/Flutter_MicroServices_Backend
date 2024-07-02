package tech.henskens.sessionservice.dto.session;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class DateRangeDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String stationIdentifier;
}

