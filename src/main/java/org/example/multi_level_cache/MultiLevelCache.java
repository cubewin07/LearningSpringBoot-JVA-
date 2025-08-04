package org.example.multi_level_cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
@Slf4j
public class MultiLevelCache implements Cache {
    private final String name;
    private final Cache local;
    private final Cache remote;


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this;
    }

    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper value = local.get(key);
        if (value != null) {
            log.warn("🔥 Caffeine HIT: {}", key);
            return value;
        }

        value = remote.get(key);
        if (value != null) {
            log.warn("🐘 Redis HIT: {}", key);
            local.put(key, value.get()); // Repopulate Caffeine
        } else {
            log.warn("💥 Cache MISS: {}", key);
        }

        return value;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        ValueWrapper value = get(key); // delegate to custom logic
        return (value != null ? (T) value.get() : null);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        ValueWrapper value = get(key); // call our custom multi-level get
        if (value != null) return (T) value.get();

        try {
            T loadedValue = valueLoader.call();
            put(key, loadedValue);
            return loadedValue;
        } catch (Exception e) {
            throw new ValueRetrievalException(key, valueLoader, e);
        }
    }

    @Override
    public void put(Object key, Object value) {
        local.put(key, value);
        remote.put(key, value);
    }

    @Override
    public void evict(Object key) {
        local.evict(key);
        remote.evict(key);
    }

    @Override
    public void clear() {
        local.clear();
        remote.clear();
    }
}
