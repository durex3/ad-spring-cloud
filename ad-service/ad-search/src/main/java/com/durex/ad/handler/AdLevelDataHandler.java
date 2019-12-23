package com.durex.ad.handler;

import com.alibaba.fastjson.JSON;
import com.durex.ad.dump.table.*;
import com.durex.ad.index.DataTable;
import com.durex.ad.index.IndexAware;
import com.durex.ad.index.adplan.AdPlanIndex;
import com.durex.ad.index.adplan.AdPlanObject;
import com.durex.ad.index.adunit.AdUnitIndex;
import com.durex.ad.index.adunit.AdUnitObject;
import com.durex.ad.index.creative.CreativeIndex;
import com.durex.ad.index.creative.CreativeObject;
import com.durex.ad.index.creativeunit.CreativeUnitIndex;
import com.durex.ad.index.creativeunit.CreativeUnitObject;
import com.durex.ad.index.district.UnitDistrictIndex;
import com.durex.ad.index.interest.UnitItIndex;
import com.durex.ad.index.keyword.UnitKeywordIndex;
import com.durex.ad.mysql.constant.OpType;
import com.durex.ad.utils.CommonUtils;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * 索引之间存在层级的划分
 * @author gelong
 * @date 2019/12/3 19:30
 */
@Slf4j
public class AdLevelDataHandler {

    private static <K, V> void handleBinlogEvent(IndexAware<K, V> index, K key, V value, OpType opType) {
        switch (opType) {
            case ADD:
                index.add(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            default:
                break;
        }
    }

    /**
     * 第二层级索引操作
     * @param adPlanTable 推广计划
     * @param opType 操作类型
     */
    public static void handleLevel2(AdPlanTable adPlanTable, OpType opType) {
        AdPlanObject adPlanObject = new AdPlanObject(
                adPlanTable.getId(),
                adPlanTable.getUserId(),
                adPlanTable.getPlanStatus(),
                adPlanTable.getStartDate(),
                adPlanTable.getEndDate()
        );
        handleBinlogEvent(DataTable.of(AdPlanIndex.class), adPlanObject.getPlanId(), adPlanObject, opType);
    }

    /**
     * 第二层级索引操作
     * @param adCreativeTable 创意
     * @param opType 操作类型
     */
    public static void handleLevel2(AdCreativeTable adCreativeTable, OpType opType) {
        CreativeObject creativeObject = new CreativeObject(
                adCreativeTable.getId(),
                adCreativeTable.getName(),
                adCreativeTable.getType(),
                adCreativeTable.getMaterialType(),
                adCreativeTable.getWidth(),
                adCreativeTable.getHeight(),
                adCreativeTable.getAuditStatus(),
                adCreativeTable.getUrl()
        );
        handleBinlogEvent(DataTable.of(CreativeIndex.class), creativeObject.getCreativeId(), creativeObject, opType);
    }

    /**
     * 第三层级索引操作
     * @param adUnitTable 推广单元
     * @param opType 操作类型
     */
    public static void handleLevel3(AdUnitTable adUnitTable, OpType opType) {
        AdPlanObject adPlanObject = DataTable.of(AdPlanIndex.class).get(adUnitTable.getPlanId());
        if (adPlanObject == null) {
            log.error("handleLevel3 found AdPlanObject error: {}", adUnitTable.getPlanId());
            return;
        }
        AdUnitObject adUnitObject = new AdUnitObject(
                adUnitTable.getId(),
                adUnitTable.getPlanId(),
                adUnitTable.getUnitStatus(),
                adUnitTable.getPositionType(),
                adPlanObject
        );
        handleBinlogEvent(DataTable.of(AdUnitIndex.class), adUnitObject.getUnitId(), adUnitObject, opType);
    }

    /**
     * 第三层级索引操作
     * @param adCreativeUnitTable 创意推广单元
     * @param opType 操作类型
     */
    public static void handleLevel3(AdCreativeUnitTable adCreativeUnitTable, OpType opType) {
        if (opType == OpType.UPDATE) {
            log.error("AdCreativeUnit index not support update ");
            return;
        }
        AdUnitObject adUnitObject = DataTable.of(AdUnitIndex.class).get(adCreativeUnitTable.getUnitId());
        CreativeObject creativeObject = DataTable.of(CreativeIndex.class).get(adCreativeUnitTable.getCreativeId());
        if (adUnitObject == null || creativeObject == null) {
            log.error("AdCreativeUnit index error: {}", JSON.toJSONString(adCreativeUnitTable));
        }
        CreativeUnitObject creativeUnitObject = new CreativeUnitObject(
                adCreativeUnitTable.getUnitId(),
                adCreativeUnitTable.getCreativeId()
        );
        handleBinlogEvent(
                DataTable.of(CreativeUnitIndex.class),
                CommonUtils.stringConcat(
                        adCreativeUnitTable.getCreativeId().toString(),
                        adCreativeUnitTable.getUnitId().toString()
                ),
                creativeUnitObject,
                opType
        );
    }

    /**
     * 第四层级索引操作
     * @param adUnitDistrictTable 地域
     * @param opType 操作类型
     */
    public static void handleLevel4(AdUnitDistrictTable adUnitDistrictTable, OpType opType) {
        if (opType == OpType.UPDATE) {
            log.error("District index not support update ");
            return;
        }
        AdUnitObject adUnitObject = DataTable.of(AdUnitIndex.class).get(adUnitDistrictTable.getUnitId());
        if (adUnitObject == null) {
            log.error("AdUnitDistrict index error: {}", adUnitDistrictTable.getUnitId());
        }
        String key = CommonUtils.stringConcat(adUnitDistrictTable.getProvince(), adUnitDistrictTable.getCity());
        Set<Long> value = Sets.newHashSet(adUnitDistrictTable.getUnitId());
        handleBinlogEvent(DataTable.of(UnitDistrictIndex.class), key, value, opType);
    }

    /**
     * 第四层级索引操作
     * @param adUnitItTable 兴趣
     * @param opType 操作类型
     */
    public static void handleLevel4(AdUnitItTable adUnitItTable, OpType opType) {
        if (opType == OpType.UPDATE) {
            log.error("It index not support update ");
            return;
        }
        AdUnitObject adUnitObject = DataTable.of(AdUnitIndex.class).get(adUnitItTable.getUnitId());
        if (adUnitObject == null) {
            log.error("AdUnitIt index error: {}", adUnitItTable.getUnitId());
        }
        Set<Long> value = Sets.newHashSet(adUnitItTable.getUnitId());
        handleBinlogEvent(DataTable.of(UnitItIndex.class), adUnitItTable.getItTag(), value, opType);
    }

    /**
     * 第四层级索引操作
     * @param adUnitKeywordTable 关键词
     * @param opType 操作类型
     */
    public static void handleLevel4(AdUnitKeywordTable adUnitKeywordTable, OpType opType) {
        if (opType == OpType.UPDATE) {
            log.error("Keyword index not support update ");
            return;
        }
        AdUnitObject adUnitObject = DataTable.of(AdUnitIndex.class).get(adUnitKeywordTable.getUnitId());
        if (adUnitObject == null) {
            log.error("AdUnitKeyword index error: {}", adUnitKeywordTable.getUnitId());
        }
        Set<Long> value = Sets.newHashSet(adUnitKeywordTable.getUnitId());
        handleBinlogEvent(DataTable.of(UnitKeywordIndex.class), adUnitKeywordTable.getKeyword(), value, opType);
    }
}
