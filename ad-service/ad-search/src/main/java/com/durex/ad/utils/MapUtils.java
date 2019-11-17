package com.durex.ad.utils;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author gelong
 * @date 2019/11/17 23:42
 */
public class MapUtils {

    public static <K, V> V getOrCreate(K key, Map<K, V> map, Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }
}
