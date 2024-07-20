package tech.henskens.stationservice.manager.Session;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.henskens.stationservice.model.User;

import java.util.NoSuchElementException;

@Service
public class UserManager implements IUserManager {
    private final RestTemplate restTemplate;
    private final Environment env;

    public UserManager(RestTemplate restTemplate, Environment env) {
        this.restTemplate = restTemplate;
        this.env = env;
    }

    @Override
    public void authenticatedUser(String bearerToken) {
        try {
            String idToken = bearerToken.substring(7);
            FirebaseAuth.getInstance().verifyIdToken(idToken);
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed", e);
        }
    }
}
