package tech.henskens.sessionservice.mapper.station;

import tech.henskens.sessionservice.dto.station.ChargingPortDto;
import tech.henskens.sessionservice.model.ChargingPort;

public class ChargingPortMapper {
    public ChargingPortMapper() {
    }

    public ChargingPortDto toChargingPortDto(ChargingPort chargingPort) {
        ChargingPortDto dto = new ChargingPortDto();
        dto.setPortIdentifier(chargingPort.getPortIdentifier());
        dto.setStatus(chargingPort.getStatus());
        return dto;
    }
}
