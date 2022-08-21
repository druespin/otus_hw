package ru.otus.cache.cachehw;


import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы

    private final Map<K, V> cacheMap = new WeakHashMap<>();
    private final List<HwListener<K, V>> hwListenerList = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cacheMap.put(key, value);
    }

    @Override
    public void remove(K key) {
        cacheMap.remove(key);
    }

    @Override
    public V get(K key) {
        return cacheMap.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        hwListenerList.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        hwListenerList.remove(listener);
    }
}
