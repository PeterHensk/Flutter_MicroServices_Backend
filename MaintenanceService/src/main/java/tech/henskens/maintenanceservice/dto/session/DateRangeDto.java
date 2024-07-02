package tech.henskens.maintenanceservice.dto.session;

import java.time.LocalDateTime;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DateRangeDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String stationIdentifier;
}
