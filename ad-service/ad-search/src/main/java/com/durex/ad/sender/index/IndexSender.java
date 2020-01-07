package com.durex.ad.sender.index;

import com.alibaba.fastjson.JSON;
import com.durex.ad.dump.table.*;
import com.durex.ad.handler.AdLevelDataHandler;
import com.durex.ad.index.DataLevel;
import com.durex.ad.mysql.constant.Constant;
import com.durex.ad.mysql.dto.MySqlRowData;
import com.durex.ad.sender.ISender;
import com.durex.ad.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gelong
 * @date 2020/1/7 21:00
 */
@Slf4j
@Component
public class IndexSender implements ISender {

    @Override
    public void sender(MySqlRowData rowData) {
        String level = rowData.getLevel();
        if (DataLevel.LEVEL2.toString().equals(level)) {
            level2RowData(rowData);
        } else if (DataLevel.LEVEL3.toString().equals(level)) {
            level3RowData(rowData);
        } else if (DataLevel.LEVEL4.toString().equals(level)) {
            level4RowData(rowData);
        } else {
            log.error("MySqlRowData error: {}", JSON.toJSONString(rowData));
        }
    }

    /**
     * 第二层级数据投递
     * @param rowData 数据
     */
    private void level2RowData(MySqlRowData rowData) {

        if (rowData.getTableName().equals(
                Constant.AdPlanTableInfo.TABLE_NAME)) {
            List<AdPlanTable> planTables = new ArrayList<>();
            for (Map<String, String> fieldValueMap :
                    rowData.getFieldValueMap()) {
                AdPlanTable planTable = new AdPlanTable();
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AdPlanTableInfo.COLUMN_ID:
                            planTable.setId(Long.valueOf(v));
                            break;
                        case Constant.AdPlanTableInfo.COLUMN_USER_ID:
                            planTable.setUserId(Long.valueOf(v));
                            break;
                        case Constant.AdPlanTableInfo.COLUMN_PLAN_STATUS:
                            planTable.setPlanStatus(Integer.valueOf(v));
                            break;
                        case Constant.AdPlanTableInfo.COLUMN_START_DATE:
                            planTable.setStartDate(
                                    DateUtils.parse(v)
                            );
                            break;
                        case Constant.AdPlanTableInfo.COLUMN_END_DATE:
                            planTable.setEndDate(
                                    DateUtils.parse(v)
                            );
                            break;
                        default:
                            break;
                    }
                });

                planTables.add(planTable);
            }
            planTables.forEach(p ->
                    AdLevelDataHandler.handleLevel2(p, rowData.getOpType()));
        } else if (rowData.getTableName().equals(
                Constant.AdCreativeTableInfo.TABLE_NAME)) {
            List<AdCreativeTable> creativeTables = new ArrayList<>();
            for (Map<String, String> fieldValeMap :
                    rowData.getFieldValueMap()) {
                AdCreativeTable creativeTable = new AdCreativeTable();
                fieldValeMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AdCreativeTableInfo.COLUMN_ID:
                            creativeTable.setId(Long.valueOf(v));
                            break;
                        case Constant.AdCreativeTableInfo.COLUMN_TYPE:
                            creativeTable.setType(Integer.valueOf(v));
                            break;
                        case Constant.AdCreativeTableInfo.COLUMN_MATERIAL_TYPE:
                            creativeTable.setMaterialType(Integer.valueOf(v));
                            break;
                        case Constant.AdCreativeTableInfo.COLUMN_HEIGHT:
                            creativeTable.setHeight(Integer.valueOf(v));
                            break;
                        case Constant.AdCreativeTableInfo.COLUMN_WIDTH:
                            creativeTable.setWidth(Integer.valueOf(v));
                            break;
                        case Constant.AdCreativeTableInfo.COLUMN_AUDIT_STATUS:
                            creativeTable.setAuditStatus(Integer.valueOf(v));
                            break;
                        case Constant.AdCreativeTableInfo.COLUMN_URL:
                            creativeTable.setUrl(v);
                            break;
                        default:
                            break;
                    }
                });
                creativeTables.add(creativeTable);
            }
            creativeTables.forEach(c ->
                    AdLevelDataHandler.handleLevel2(c, rowData.getOpType()));
        }
    }

    /**
     * 第三层级数据投递
     * @param rowData 数据
     */
    private void level3RowData(MySqlRowData rowData) {
        if (rowData.getTableName().equals(
                Constant.AdUnitTableInfo.TABLE_NAME)) {
            List<AdUnitTable> unitTables = new ArrayList<>();
            for (Map<String, String> fieldValueMap :
                    rowData.getFieldValueMap()) {
                AdUnitTable unitTable = new AdUnitTable();
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AdUnitTableInfo.COLUMN_ID:
                            unitTable.setId(Long.valueOf(v));
                            break;
                        case Constant.AdUnitTableInfo.COLUMN_UNIT_STATUS:
                            unitTable.setUnitStatus(Integer.valueOf(v));
                            break;
                        case Constant.AdUnitTableInfo.COLUMN_POSITION_TYPE:
                            unitTable.setPositionType(Integer.valueOf(v));
                            break;
                        case Constant.AdUnitTableInfo.COLUMN_PLAN_ID:
                            unitTable.setPlanId(Long.valueOf(v));
                            break;
                        default:
                            break;
                    }
                });
                unitTables.add(unitTable);
            }
            unitTables.forEach(u ->
                    AdLevelDataHandler.handleLevel3(u, rowData.getOpType()));
        } else if (rowData.getTableName().equals(
                Constant.AdCreativeUnitTableInfo.TABLE_NAME)) {
            List<AdCreativeUnitTable> creativeUnitTables = new ArrayList<>();
            for (Map<String, String> fieldValueMap :
                    rowData.getFieldValueMap()) {
                AdCreativeUnitTable creativeUnitTable = new AdCreativeUnitTable();
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AdCreativeUnitTableInfo.COLUMN_CREATIVE_ID:
                            creativeUnitTable.setCreativeId(Long.valueOf(v));
                            break;
                        case Constant.AdCreativeUnitTableInfo.COLUMN_UNIT_ID:
                            creativeUnitTable.setUnitId(Long.valueOf(v));
                            break;
                        default:
                            break;
                    }
                });
                creativeUnitTables.add(creativeUnitTable);
            }
            creativeUnitTables.forEach(
                    u -> AdLevelDataHandler.handleLevel3(u, rowData.getOpType())
            );
        }
    }

    /**
     * 第四层级数据投递
     * @param rowData 数据
     */
    private void level4RowData(MySqlRowData rowData) {
        switch (rowData.getTableName()) {
            case Constant.AdUnitDistrictTableInfo.TABLE_NAME:
                List<AdUnitDistrictTable> districtTables = new ArrayList<>();
                for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                    AdUnitDistrictTable districtTable = new AdUnitDistrictTable();
                    fieldValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.AdUnitDistrictTableInfo.COLUMN_UNIT_ID:
                                districtTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.AdUnitDistrictTableInfo.COLUMN_PROVINCE:
                                districtTable.setProvince(v);
                                break;
                            case Constant.AdUnitDistrictTableInfo.COLUMN_CITY:
                                districtTable.setCity(v);
                                break;
                            default:
                                break;
                        }
                    });
                    districtTables.add(districtTable);
                }
                districtTables.forEach(
                        d -> AdLevelDataHandler.handleLevel4(d, rowData.getOpType())
                );
                break;
            case Constant.AdUnitItTableInfo.TABLE_NAME:
                List<AdUnitItTable> itTables = new ArrayList<>();
                for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                    AdUnitItTable itTable = new AdUnitItTable();
                    fieldValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.AdUnitItTableInfo.COLUMN_UNIT_ID:
                                itTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.AdUnitItTableInfo.COLUMN_IT_TAG:
                                itTable.setItTag(v);
                                break;
                            default:
                                break;
                        }
                    });
                    itTables.add(itTable);
                }
                itTables.forEach(
                        i -> AdLevelDataHandler.handleLevel4(i, rowData.getOpType())
                );
                break;
            case Constant.AdUnitKeywordTableInfo.TABLE_NAME:
                List<AdUnitKeywordTable> keywordTables = new ArrayList<>();
                for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                    AdUnitKeywordTable keywordTable = new AdUnitKeywordTable();
                    fieldValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.AdUnitKeywordTableInfo.COLUMN_UNIT_ID:
                                keywordTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.AdUnitKeywordTableInfo.COLUMN_KEYWORD:
                                keywordTable.setKeyword(v);
                                break;
                            default:
                                break;
                        }
                    });
                    keywordTables.add(keywordTable);
                }
                keywordTables.forEach(
                        k -> AdLevelDataHandler.handleLevel4(k, rowData.getOpType())
                );
                break;
            default:
                break;
        }
    }
}
