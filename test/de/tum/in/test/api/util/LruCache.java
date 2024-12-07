package de.tum.in.test.api.util;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implements a LRU (least recently used) cache as described in
 * {@link LinkedHashMap#removeEldestEntry}. This class is not thread safe.
 *
 * @param <K> the type of the cache keys
 * @param <V> the type of the cache values
 * @author Christian Femers
 * @version 1.0.0
 * @since 1.3.4
 */
@API(status = Status.STABLE)
public class LruCache<K, V> extends LinkedHashMap<K, V> {

    private static final long serialVersionUID = 1L;

    private final int maxCacheSize;

    public LruCache(int maxCacheSize) {
        // plus one because the old entry gets evicted after insertion of the new one
        super(maxCacheSize + 1, 0.75f, true);
        this.maxCacheSize = maxCacheSize;
    }

    public int getMaxCacheSize() {
        return maxCacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxCacheSize;
    }
}
