package tech.henskens.stationservice.manager;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import tech.henskens.stationservice.dto.ChargingPortDto;
import tech.henskens.stationservice.dto.ChargingPortStatusDto;
import tech.henskens.stationservice.dto.StationDto;
import org.springframework.data.domain.Page;

public interface IStationManager {
    void createStation(StationDto stationDto);

    Page<StationDto> getAllStations(Pageable pageable);

    Optional<ChargingPortDto> getChargingPort(String stationIdentifier, String portIdentifier);

    void updateChargingPortStatus(String stationIdentifier, String portIdentifier, ChargingPortStatusDto statusDto);

    Optional<StationDto> getStation(String stationIdentifier);
}

