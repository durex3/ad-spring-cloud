package com.durex.ad.index;

/**
 * @author gelong
 * @date 2019/11/17 21:30
 */
public interface IndexAware<K, V> {

    /**
     * 返回索引
     * @param key key
     * @return V value
     */
    V get(K key);

    /**
     * 添加索引
     * @param key key
     * @param value value
     */
    void add(K key, V value);

    /**
     * 更新索引
     * @param key key
     * @param value value
     */
    void update(K key, V value);

    /**
     * 删除索引
     * @param key key
     * @param value value
     */
    void delete(K key, V value);
}
