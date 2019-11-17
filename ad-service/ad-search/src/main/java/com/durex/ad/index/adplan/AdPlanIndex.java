package com.durex.ad.index.adplan;

import com.durex.ad.index.IndexAware;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * @author gelong
 * @date 2019/11/17 21:41
 */
@Slf4j
@Component
public class AdPlanIndex implements IndexAware<Long, AdPlanObject> {

    private static Map<Long, AdPlanObject> objectMap;

    static {
        objectMap = Maps.newConcurrentMap();
    }

    @Override
    public AdPlanObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdPlanObject value) {
        log.info("AdPlanIndex before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("AdPlanIndex after add: {}", objectMap);
    }

    @Override
    public void update(Long key, AdPlanObject value) {
        log.info("AdPlanIndex before update: {}", objectMap);
        AdPlanObject oldObject = objectMap.get(key);
        if (oldObject == null) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }
        log.info("AdPlanIndex after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, AdPlanObject value) {
        log.info("AdPlanIndex delete add: {}", objectMap);
        objectMap.remove(key);
        log.info("AdPlanIndex delete add: {}", objectMap);
    }
}
