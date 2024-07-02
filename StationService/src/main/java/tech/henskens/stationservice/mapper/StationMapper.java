package tech.henskens.stationservice.mapper;

import org.springframework.stereotype.Component;
import tech.henskens.stationservice.dto.ChargingPortDto;
import tech.henskens.stationservice.dto.StationDto;
import tech.henskens.stationservice.model.ChargingPort;
import tech.henskens.stationservice.model.Station;

@Component
public class StationMapper {

    public Station dtoToStation(StationDto stationDto) {
        if (stationDto == null) {
            return null;
        }

        return Station.builder()
                .stationIdentifier(stationDto.getStationIdentifier())
                .location(stationDto.getLocation())
                .chargingPorts(stationDto.getChargingPorts())
                .imageId(stationDto.getImageId())
                .build();
    }

    public StationDto stationToDto(Station station) {
        if (station == null) {
            return null;
        }

        return StationDto.builder()
                .stationIdentifier(station.getStationIdentifier())
                .location(station.getLocation())
                .chargingPorts(station.getChargingPorts())
                .imageId(station.getImageId())
                .build();
    }

    public ChargingPortDto toChargingPortDto(ChargingPort chargingPort) {
        if (chargingPort == null) {
            return null;
        }

        return ChargingPortDto.builder()
                .portIdentifier(chargingPort.getPortIdentifier())
                .status(chargingPort.getStatus())
                .build();
    }
}
