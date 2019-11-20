package com.durex.ad.index.district;

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
 * @date 2019/11/19 1:23
 */
@Slf4j
@Component
public class UnitDistrictIndex implements IndexAware<String, Set<Long>> {

    private static Map<String, Set<Long>> districtUnitMap;
    private static Map<Long, Set<String>> unitDistrictMap;

    static {
        districtUnitMap = Maps.newConcurrentMap();
        unitDistrictMap = Maps.newConcurrentMap();
    }

    @Override
    public Set<Long> get(String key) {
        if (StringUtils.isEmpty(key)) {
            return Sets.newHashSet();
        }
        Set<Long> result = districtUnitMap.get(key);
        if (CollectionUtils.isEmpty(result)) {
            return Sets.newHashSet();
        }
        return result;
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitDistrictIndex before add: {}", districtUnitMap);
        Set<Long> unitIdSet = MapUtils.getOrCreate(key, districtUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.addAll(value);
        value.forEach(v -> {
            Set<String> districtSet = MapUtils.getOrCreate(v, unitDistrictMap, ConcurrentSkipListSet::new);
            districtSet.add(key);
        });
        log.info("UnitDistrictIndex after add: {}", districtUnitMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("district index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitDistrictIndex before delete: {}", districtUnitMap);
        Set<Long> unitIdSet = MapUtils.getOrCreate(key, districtUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.removeAll(value);
        value.forEach(v -> {
            Set<String> districtSet = MapUtils.getOrCreate(v, unitDistrictMap, ConcurrentSkipListSet::new);
            districtSet.remove(key);
        });
        log.info("UnitDistrictIndex after delete: {}", districtUnitMap);
    }

    /**
     * 根据推广单元取匹配地域
     * @param unitId 推广单元
     * @param districts 地域
     * @return boolean
     */
    public boolean match(Long unitId, List<String> districts) {
        if (CollectionUtils.isEmpty(districts) || !unitDistrictMap.containsKey(unitId)) {
            return false;
        }
        Set<String> districtSet = unitDistrictMap.get(unitId);
        // districts是districtSet的子集
        return CollectionUtils.isSubCollection(districts, districtSet);
    }
}
