package org.example.object_storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
