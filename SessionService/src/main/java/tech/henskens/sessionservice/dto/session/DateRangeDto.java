package tech.henskens.sessionservice.dto.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DateRangeDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String stationIdentifier;
}

