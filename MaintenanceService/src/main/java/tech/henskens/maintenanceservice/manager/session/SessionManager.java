package tech.henskens.maintenanceservice.manager.session;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.henskens.maintenanceservice.dto.session.SessionCountDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Service
public class SessionManager implements ISessionManager {
    private final RestTemplate restTemplate;
    @Value("${session.backend.url}")
    private String sessionBackendUrl;

    public SessionManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Optional<SessionCountDto> getSessionCount(String token, String stationIdentifier, LocalDateTime startDate, LocalDateTime endDate) {
        String url = String.format("%s/session/count?startDate=%s&endDate=%s&stationIdentifier=%s", this.sessionBackendUrl, startDate.toString(), endDate.toString(), stationIdentifier);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<SessionCountDto> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, SessionCountDto.class);
        return Optional.ofNullable(response.getBody());
    }
}
