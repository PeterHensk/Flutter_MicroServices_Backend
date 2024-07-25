package tech.henskens.maintenanceservice.manager.station;

import org.springframework.http.ResponseEntity;
import tech.henskens.maintenanceservice.dto.station.StationDto;
import tech.henskens.maintenanceservice.dto.station.ChargingPortStatusDto;

public interface IStationManager {
    ResponseEntity<StationDto> getStation(String token, String stationId);

    void updateChargingPortStatus(String token, ChargingPortStatusDto chargingPortStatusDto);
}
