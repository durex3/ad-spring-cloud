package com.durex.ad.sender;

import com.alibaba.fastjson.JSON;
import com.durex.ad.dump.table.AdCreativeTable;
import com.durex.ad.dump.table.AdPlanTable;
import com.durex.ad.handler.AdLevelDataHandler;
import com.durex.ad.index.DataLevel;
import com.durex.ad.mysql.constant.Constant;
import com.durex.ad.mysql.dto.MySqlRowData;
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

        } else if (DataLevel.LEVEL4.toString().equals(level)) {

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
}
