package tech.henskens.maintenanceservice.manager.station;

import tech.henskens.maintenanceservice.dto.station.StationDto;

public interface IStationManager {
    StationDto getStation(String stationId);
}
