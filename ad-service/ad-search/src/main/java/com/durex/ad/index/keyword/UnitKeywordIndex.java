package com.durex.ad.index.keyword;

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
 * @date 2019/11/17 23:29
 */
@Slf4j
@Component
public class UnitKeywordIndex implements IndexAware<String, Set<Long>> {

    private static Map<String, Set<Long>> keywordUnitMap;
    private static Map<Long, Set<String>> unitKeywordMap;

    static {
        keywordUnitMap = Maps.newConcurrentMap();
        unitKeywordMap = Maps.newConcurrentMap();
    }

    @Override
    public Set<Long> get(String key) {
        if (StringUtils.isEmpty(key)) {
            return Sets.newHashSet();
        }
        Set<Long> result = keywordUnitMap.get(key);
        if (CollectionUtils.isEmpty(result)) {
            return Sets.newHashSet();
        }
        return result;
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitKeywordIndex before add: {}", keywordUnitMap);
        Set<Long> unitIdSet = MapUtils.getOrCreate(key, keywordUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.addAll(value);
        // 推广单元 -> 关联词列表
        value.forEach(v -> {
            Set<String> keywordSet = MapUtils.getOrCreate(v, unitKeywordMap, ConcurrentSkipListSet::new);
            keywordSet.add(key);
        });
        log.info("UnitKeywordIndex after add: {}", keywordUnitMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("keyword index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("AdUnitIndex delete add: {}", keywordUnitMap);
        Set<Long> unitIdSet = MapUtils.getOrCreate(key, keywordUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.removeAll(value);
        // 推广单元 -> 关联词列表
        value.forEach(v -> {
            Set<String> keywordSet = MapUtils.getOrCreate(v, unitKeywordMap, ConcurrentSkipListSet::new);
            keywordSet.remove(key);
        });
        log.info("AdUnitIndex delete add: {}", keywordUnitMap);
    }

    public boolean match(Long unitId, List<String> keywords) {
        if (CollectionUtils.isEmpty(keywords) || !unitKeywordMap.containsKey(unitId)) {
            return false;
        }
        Set<String> keywordSet = unitKeywordMap.get(unitId);
        // keywords是keywordSet的子集
        return CollectionUtils.isSubCollection(keywords, keywordSet);
    }
}
