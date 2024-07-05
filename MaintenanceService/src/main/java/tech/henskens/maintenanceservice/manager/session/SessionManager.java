package tech.henskens.maintenanceservice.manager.session;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.henskens.maintenanceservice.dto.session.SessionCountDto;

@Service
public class SessionManager implements ISessionManager {
    private final RestTemplate restTemplate;
    @Value("${session.backend.url}")
    private String sessionBackendUrl;

    public SessionManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SessionCountDto getSessionCount(String stationIdentifier, LocalDateTime startDate, LocalDateTime endDate) {
        String url = String.format("%s/session/count?startDate=%s&endDate=%s&stationIdentifier=%s", this.sessionBackendUrl, startDate.toString(), endDate.toString(), stationIdentifier);
        ResponseEntity<SessionCountDto> response = this.restTemplate.getForEntity(url, SessionCountDto.class);
        SessionCountDto sessionCountDto = response.getBody();
        System.out.println(sessionCountDto);
        return sessionCountDto;
    }
}
