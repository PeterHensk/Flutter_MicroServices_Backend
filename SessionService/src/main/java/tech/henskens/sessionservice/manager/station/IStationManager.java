package tech.henskens.sessionservice.manager.station;

import java.util.Optional;
import tech.henskens.sessionservice.dto.station.ChargingPortDto;
import tech.henskens.sessionservice.dto.station.ChargingPortStatusDto;

public interface IStationManager {
    Optional<ChargingPortDto> getChargingPort(String token, String stationIdentifier, String portIdentifier);
    void updateChargingPortStatus(String token, ChargingPortStatusDto chargingPortStatusDto);
}
