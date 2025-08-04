package org.example.multi_level_cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class MultiLevelCacheManager implements CacheManager {
    private final CacheManager caffeineCacheManager;
    private final CacheManager redisCacheManager;

    private Map<String, Cache> caches = new ConcurrentHashMap<>();

    @Override
    public Cache getCache(String name) {
        return caches.computeIfAbsent(name, cacheName -> {
            Cache caffeineCache = caffeineCacheManager.getCache(cacheName);
            Cache redisCache = redisCacheManager.getCache(cacheName);
            return new MultiLevelCache(cacheName, caffeineCache, redisCache);
        });
    }

    @Override
    public Collection<String> getCacheNames() {
        return redisCacheManager.getCacheNames();
    }
}
