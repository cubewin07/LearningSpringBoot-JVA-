package org.example.rate_limiting;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class RateLimiterService {
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String key) {
        return cache.computeIfAbsent(key, this::newBucket);
    }

    public Bucket newBucket(String key) {
        return Bucket.builder()
                .addLimit(limit -> limit.capacity(10).refillIntervally(10, Duration.ofMinutes(1)))
                .build();
    }

}
