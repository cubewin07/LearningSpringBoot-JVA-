package org.example.object_storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.identity.spi.AwsCredentialsIdentity;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class R2_config {
    @Value("${cloudflare.r2.bucketName}")
    private String bucketName;

    @Value("${CLOUDFLARE_R2_ACCESS_KEY_ID}")
    private String accessKey;
    @Value("${CLOUDFLARE_R2_SECRET_ACCESS_KEY}")
    private String secretKey;
    @Value("${CLOUDFLARE_R2_ENDPOINT_URL}")
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
