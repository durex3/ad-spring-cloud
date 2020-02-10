package com.durex.ad.index.creative;

import com.durex.ad.index.IndexAware;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author gelong
 * @date 2019/11/19 1:45
 */
@Slf4j
@Component
public class CreativeIndex implements IndexAware<Long, CreativeObject> {

    private static Map<Long, CreativeObject> objectMap;

    static {
        objectMap = Maps.newConcurrentMap();
    }

    @Override
    public CreativeObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, CreativeObject value) {
        log.info("CreativeIndex before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("CreativeIndex after add: {}", objectMap);
    }

    @Override
    public void update(Long key, CreativeObject value) {
        log.info("CreativeIndex before update: {}", objectMap);
        CreativeObject creativeObject = objectMap.get(key);
        if (creativeObject == null) {
            objectMap.put(key, value);
        } else {
            creativeObject.update(value);
        }
        log.info("CreativeIndex after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, CreativeObject value) {
        log.info("CreativeIndex before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("CreativeIndex after delete: {}", objectMap);
    }

    public List<CreativeObject> fetch(Collection<Long> adIds) {

        if (CollectionUtils.isEmpty(adIds)) {
            return Collections.emptyList();
        }

        List<CreativeObject> result = new ArrayList<>();

        adIds.forEach(u -> {
            CreativeObject object = get(u);
            if (null == object) {
                log.error("CreativeObject not found: {}", u);
                return;
            }

            result.add(object);
        });

        return result;
    }
}
