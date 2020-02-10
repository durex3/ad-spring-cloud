package com.durex.ad.index.creativeunit;

import com.durex.ad.index.IndexAware;
import com.durex.ad.index.adunit.AdUnitObject;
import com.durex.ad.utils.MapUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author gelong
 * @date 2019/11/19 1:59
 */
@Slf4j
@Component
public class CreativeUnitIndex implements IndexAware<String, CreativeUnitObject> {

    /**
     * <creativeId-unitId, CreativeUnitObject>
     */
    private static Map<String, CreativeUnitObject> objectMap;

    /**
     * <creativeId, unitIdSet>
     */
    private static Map<Long, Set<Long>> creativeUnitMap;

    /**
     * <unitId, creativeIdSet>
     */
    private static Map<Long, Set<Long>> unitCreativeMap;

    static {
        objectMap = Maps.newConcurrentMap();
        creativeUnitMap = Maps.newConcurrentMap();
        unitCreativeMap = Maps.newConcurrentMap();
    }

    @Override
    public CreativeUnitObject get(String key) {
        return objectMap.get(key);
    }

    @Override
    public void add(String key, CreativeUnitObject value) {
        log.info("CreativeUnitIndex before add: {}", objectMap);
        objectMap.put(key, value);
        Set<Long> unitIdSet = MapUtils.getOrCreate(value.getCreativeId(), creativeUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.add(value.getUnitId());
        Set<Long> creativeIdSet = MapUtils.getOrCreate(value.getUnitId(), unitCreativeMap, ConcurrentSkipListSet::new);
        creativeIdSet.add(value.getCreativeId());
        log.info("CreativeUnitIndex after add: {}", objectMap);
    }

    @Override
    public void update(String key, CreativeUnitObject value) {
        log.error("Creative Unit index can not support update");
    }

    @Override
    public void delete(String key, CreativeUnitObject value) {
        log.info("CreativeUnitIndex before delete: {}", objectMap);
        objectMap.remove(key);
        Set<Long> unitIdSet = creativeUnitMap.get(value.getCreativeId());
        if (CollectionUtils.isNotEmpty(unitIdSet)) {
            unitIdSet.remove(value.getCreativeId());
        }
        Set<Long> creativeIdSet = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isNotEmpty(creativeIdSet)) {
            creativeIdSet.remove(value.getUnitId());
        }
        log.info("CreativeUnitIndex before delete: {}", objectMap);
    }


    public List<Long> selectAds(List<AdUnitObject> unitObjects) {

        if (CollectionUtils.isEmpty(unitObjects)) {
            return Collections.emptyList();
        }

        List<Long> result = new ArrayList<>();

        for (AdUnitObject unitObject : unitObjects) {

            Set<Long> adIds = unitCreativeMap.get(unitObject.getUnitId());
            if (CollectionUtils.isNotEmpty(adIds)) {
                result.addAll(adIds);
            }
        }

        return result;
    }
}
