package tech.henskens.sessionservice.manager.station;

import java.util.Optional;
import tech.henskens.sessionservice.dto.station.ChargingPortDto;

public interface IStationManager {
    Optional<ChargingPortDto> getChargingPort(String stationIdentifier, String portIdentifier);
    void updateChargingPortStatus(String stationIdentifier, String portIdentifier, String status);
}
