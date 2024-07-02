package tech.henskens.maintenanceservice.dto.maintenance;

import java.time.LocalDateTime;
import tech.henskens.maintenanceservice.model.IssueCategory;
import tech.henskens.maintenanceservice.model.Status;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceDto {
    private Long id;
    private String stationIdentifier;
    private IssueCategory issueCategory;
    private String issueDescription;
    private LocalDateTime creationDate;
    private LocalDateTime maintenanceDate;
    private Status status;
}

