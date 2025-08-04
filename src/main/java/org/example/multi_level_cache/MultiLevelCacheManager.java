package org.example.multi_level_cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class MultiLevelCacheManager implements CacheManager {
    private final CacheManager caffeineCacheManager;
    private final RedisCacheManager redisCacheManager;


    @Override
    public Cache getCache(String name) {
        Cache local = caffeineCacheManager.getCache(name);
        Cache remote = redisCacheManager.getCache(name);
        return new MultiLevelCache(name, local, remote);
    }

    @Override
    public Collection<String> getCacheNames() {
        return redisCacheManager.getCacheNames();
    }
}
