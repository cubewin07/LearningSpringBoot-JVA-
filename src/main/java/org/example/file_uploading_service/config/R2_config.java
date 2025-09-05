package org.example.file_uploading_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class R2_config {
    @Value("${cloudflare.r2.bucketName}")
    private String bucketName;

    @Value("${cloudflare.r2.access.key.id}")
    private String accessKey;
    @Value("${cloudflare.r2.secret.access.key}")
    private String secretKey;
    @Value("${cloudflare.r2.endpoint.url}")
    private String endpoint;

    @Bean
   public S3Client r2Client() {
        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.of("auto"))
                .credentialsProvider(() -> credentials)
                .build();

    }
}
