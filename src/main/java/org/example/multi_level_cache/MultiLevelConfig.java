package org.example.multi_level_cache;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;

@Configuration
public class MultiLevelConfig {

    @Bean
    @Primary
    public CacheManager multiLevelCacheManager(
            @Qualifier("caffeineCacheManager")CacheManager caffeine,
            @Qualifier("redisCacheManager") CacheManager redis) {
        return new MultiLevelCacheManager(caffeine, redis);
    }

}
