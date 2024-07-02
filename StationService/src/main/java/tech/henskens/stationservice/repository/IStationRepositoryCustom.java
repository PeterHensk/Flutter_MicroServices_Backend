package tech.henskens.stationservice.repository;

import tech.henskens.stationservice.model.ChargingPortStatus;

public interface IStationRepositoryCustom {
    void updateChargingPortStatus(String stationIdentifier, String portIdentifier, ChargingPortStatus status);
}
