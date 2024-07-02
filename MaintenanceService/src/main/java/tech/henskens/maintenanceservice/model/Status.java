package tech.henskens.maintenanceservice.model;

import lombok.Getter;

@Getter
public enum Status {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED,
    REQUESTED;

    private Status() {
    }
}
