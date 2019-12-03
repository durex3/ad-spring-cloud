package com.durex.ad.handler;

import com.alibaba.fastjson.JSON;
import com.durex.ad.dump.table.AdCreativeTable;
import com.durex.ad.dump.table.AdCreativeUnitTable;
import com.durex.ad.dump.table.AdPlanTable;
import com.durex.ad.dump.table.AdUnitTable;
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
import com.durex.ad.mysql.OpType;
import com.durex.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;

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
}
