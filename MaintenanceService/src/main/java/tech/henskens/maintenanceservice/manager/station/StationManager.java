package tech.henskens.maintenanceservice.manager.station;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.henskens.maintenanceservice.dto.station.StationDto;

import tech.henskens.maintenanceservice.dto.station.ChargingPortStatusDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StationManager implements IStationManager {
    private final RestTemplate restTemplate;
    @Value("${station.backend.url}")
    private String stationdbBackendUrl;

    public StationManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<StationDto> getStation(String token, String stationIdentifier) {
        String url = String.format("%s/station/%s", this.stationdbBackendUrl, stationIdentifier);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<StationDto> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, StationDto.class);
        return ResponseEntity.of(Optional.ofNullable(response.getBody()));
    }

    public void updateChargingPortStatus(String token, ChargingPortStatusDto chargingPortStatusDto) {
        String url = String.format("%s/station/updateStationPort", this.stationdbBackendUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        Map<String, String> updates = new HashMap<>();
        updates.put("stationIdentifier", chargingPortStatusDto.getStationIdentifier());
        updates.put("portIdentifier", chargingPortStatusDto.getPortIdentifier());
        updates.put("status", chargingPortStatusDto.getStatus());
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(updates, headers);
        this.restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
    }
}
