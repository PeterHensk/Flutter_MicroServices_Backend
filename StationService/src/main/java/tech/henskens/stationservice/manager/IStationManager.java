package tech.henskens.stationservice.manager;

import java.util.List;
import java.util.Optional;
import tech.henskens.stationservice.dto.ChargingPortDto;
import tech.henskens.stationservice.dto.ChargingPortStatusDto;
import tech.henskens.stationservice.dto.StationDto;

public interface IStationManager {
    void createStation(StationDto stationDto);

    List<StationDto> getAllStations();

    Optional<ChargingPortDto> getChargingPort(String stationIdentifier, String portIdentifier);

    void updateChargingPortStatus(String stationIdentifier, String portIdentifier, ChargingPortStatusDto statusDto);

    Optional<StationDto> getStation(String stationIdentifier);
}

