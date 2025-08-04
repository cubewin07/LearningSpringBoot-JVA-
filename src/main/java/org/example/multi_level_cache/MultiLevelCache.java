package org.example.multi_level_cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
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
        ValueWrapper localValue = local.get(key);
        if(localValue != null) return localValue;


        ValueWrapper remoteValue = remote.get(key);
        if(remoteValue != null) local.put(key, remoteValue.get());
        return remoteValue;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        T localValue = local.get(key, type);
        if(localValue != null) return localValue;

        T remoteValue = remote.get(key, type);
        if(remoteValue != null) local.put(key, remoteValue);
        return remoteValue;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        T localValue = local.get(key, valueLoader);
        if(localValue != null) return localValue;

        T remoteValue = remote.get(key, valueLoader);
        if(remoteValue != null) local.put(key, remoteValue);
        return remoteValue;
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
