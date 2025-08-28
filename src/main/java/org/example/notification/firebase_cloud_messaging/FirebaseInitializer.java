package org.example.notification.firebase_cloud_messaging;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import jakarta.annotation.PostConstruct;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

class FirebaseEnabledCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String enabled = context.getEnvironment().getProperty("spring.firebase.enabled", "false");
        return Boolean.parseBoolean(enabled);
    }
}

@Component
@Conditional(FirebaseEnabledCondition.class)
public class FirebaseInitializer {

    @Value("${firebase.credentials.path:/app/firebaseServiceAccountKey_messaging.json}")
    private String credentialsPath;

    @PostConstruct
    public void init() throws IOException {
        try (FileInputStream serviceAccount = new FileInputStream(credentialsPath)) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
        }
    }
}
