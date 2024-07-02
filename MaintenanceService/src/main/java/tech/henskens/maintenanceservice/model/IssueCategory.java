package tech.henskens.maintenanceservice.model;

import lombok.Getter;

@Getter
public enum IssueCategory {
    ELECTRICAL,
    MECHANICAL,
    SOFTWARE,
    OTHER;

    private IssueCategory() {
    }
}
