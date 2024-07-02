package tech.henskens.sessionservice.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);

    public FirebaseConfig() {
    }

    @Bean
    public FirebaseApp initializeFirebaseApp() throws IOException {
        logger.info("Initializing Firebase App...");
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/firebase-service-account.json");
        FirebaseOptions options = (new FirebaseOptions.Builder()).setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
        FirebaseApp app = FirebaseApp.initializeApp(options);
        logger.info("Firebase App initialized successfully.");
        return app;
    }
}

