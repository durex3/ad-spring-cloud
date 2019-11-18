package com.durex.ad.index.interest;

import com.durex.ad.index.IndexAware;
import com.durex.ad.utils.MapUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author gelong
 * @date 2019/11/19 0:51
 */
@Slf4j
@Component
public class UnitItIndex implements IndexAware<String, Set<Long>> {

    private static Map<String, Set<Long>> itUnitMap;
    private static Map<Long, Set<String>> unitItMap;

    static {
        itUnitMap = Maps.newConcurrentMap();
        unitItMap = Maps.newConcurrentMap();
    }

    @Override
    public Set<Long> get(String key) {
        if (StringUtils.isEmpty(key)) {
            return Sets.newHashSet();
        }
        Set<Long> result = itUnitMap.get(key);
        if (CollectionUtils.isEmpty(result)) {
            return Sets.newHashSet();
        }
        return result;
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitItIndex before add: {}", itUnitMap);
        Set<Long> unitIdSet = MapUtils.getOrCreate(key, itUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.addAll(value);
        value.forEach(v -> {
            Set<String> itTagSet = MapUtils.getOrCreate(v, unitItMap, ConcurrentSkipListSet::new);
            itTagSet.add(key);
        });
        log.info("UnitItIndex after add: {}", itUnitMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("it index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitItIndex before delete: {}", itUnitMap);
        Set<Long> unitIdSet = MapUtils.getOrCreate(key, itUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.removeAll(value);
        value.forEach(v -> {
            Set<String> itTagSet = MapUtils.getOrCreate(v, unitItMap, ConcurrentSkipListSet::new);
            itTagSet.remove(key);
        });
        log.info("UnitItIndex after delete: {}", itUnitMap);
    }

    /**
     * 根据推广单元取匹配兴趣tag
     * @param unitId 推广单元
     * @param itTags 兴趣
     * @return boolean
     */
    public boolean match(Long unitId, List<String> itTags) {
        if (CollectionUtils.isEmpty(itTags) || !unitItMap.containsKey(unitId)) {
            return false;
        }
        Set<String> itTagSet = unitItMap.get(unitId);
        // itTags是itTagSet的子集
        return CollectionUtils.isSubCollection(itTags, itTagSet);
    }
}
