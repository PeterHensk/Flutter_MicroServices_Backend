package tech.henskens.sessionservice.manager.station;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.henskens.sessionservice.dto.station.ChargingPortDto;

@Service
public class StationManager implements IStationManager {
    private final RestTemplate restTemplate;
    @Value("${station.backend.url}")
    private String stationdbBackendUrl;

    public StationManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<ChargingPortDto> getChargingPort(String stationIdentifier, String portIdentifier) {
        String url = String.format("%s/station/%s/port/%s", this.stationdbBackendUrl, stationIdentifier, portIdentifier);
        ChargingPortDto chargingPortDto = this.restTemplate.getForObject(url, ChargingPortDto.class);
        return Optional.ofNullable(chargingPortDto);
    }
}
