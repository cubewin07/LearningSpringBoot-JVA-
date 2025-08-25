package org.example.notification.firebase_cloud_messaging;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import jakarta.annotation.PostConstruct;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Component;

@Component
public class FirebaseInitializer {

    @PostConstruct
    public void init() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("/Users/letanthang/learning_software/firebaseServiceAccountKey_messaging.json");

       FirebaseOptions options = FirebaseOptions.builder()
               .setCredentials(GoogleCredentials.fromStream(serviceAccount))
               .build();

       FirebaseApp.initializeApp(options);
    }
}
