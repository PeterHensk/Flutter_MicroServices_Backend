package tech.henskens.maintenanceservice.manager.session;

import com.google.firebase.auth.FirebaseAuth;
import org.springframework.stereotype.Service;

@Service
public class UserManager implements IUserManager {

    public UserManager() {
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
