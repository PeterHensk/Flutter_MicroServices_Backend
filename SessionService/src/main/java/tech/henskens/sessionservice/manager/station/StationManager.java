package tech.henskens.sessionservice.manager.station;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    public Optional<ChargingPortDto> getChargingPort(String token, String stationIdentifier, String portIdentifier) {
        String url = String.format("%s/station/%s/port/%s", this.stationdbBackendUrl, stationIdentifier, portIdentifier);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<ChargingPortDto> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, ChargingPortDto.class);
        return Optional.ofNullable(response.getBody());
    }

    public void updateChargingPortStatus(String token,String stationIdentifier, String portIdentifier, String status) {
        String url = String.format("%s/station/%s/port/%s", this.stationdbBackendUrl, stationIdentifier, portIdentifier);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        Map<String, String> updates = new HashMap<>();
        updates.put("status", status);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(updates, headers);
        this.restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
    }
}
