package tech.henskens.maintenanceservice.manager.station;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.henskens.maintenanceservice.dto.station.StationDto;

@Service
public class StationManager implements IStationManager {
    private final RestTemplate restTemplate;
    @Value("${station.backend.url}")
    private String stationdbBackendUrl;

    public StationManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public StationDto getStation(String stationIdentifier) {
        String url = String.format("%s/station/%s", this.stationdbBackendUrl, stationIdentifier);
        StationDto stationDto = (StationDto)this.restTemplate.getForObject(url, StationDto.class, new Object[0]);
        System.out.println(stationDto);
        return stationDto;
    }
}
