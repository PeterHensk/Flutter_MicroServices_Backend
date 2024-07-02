package tech.henskens.maintenanceservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stationIdentifier;
    @Enumerated(EnumType.STRING)
    private IssueCategory issueCategory;
    private String issueDescription;
    private LocalDateTime creationDate;
    private LocalDateTime maintenanceDate;
    @Enumerated(EnumType.STRING)
    private Status status;
}
