package tech.henskens.stationservice.model;

import lombok.Getter;

@Getter
public enum ChargingPortStatus {
    AVAILABLE("Available"),
    IN_USE("In use"),
    DAMAGED("Damaged"),
    REPAIRING("Repairing");

    private final String status;

    private ChargingPortStatus(String status) {
        this.status = status;
    }

}
