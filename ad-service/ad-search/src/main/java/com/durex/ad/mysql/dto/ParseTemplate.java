package com.durex.ad.mysql.dto;

import com.durex.ad.mysql.constant.OpType;
import com.durex.ad.utils.MapUtils;
import com.google.common.collect.Maps;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gelong
 * @date 2019/12/23 20:25
 */
@Data
public class ParseTemplate {

    private String database;
    private Map<String, TableTemplate> tableTemplateMap = Maps.newHashMap();

    public static ParseTemplate parse(Template template) {
        ParseTemplate parseTemplate = new ParseTemplate();
        parseTemplate.setDatabase(template.getDatabase());
        template.getTableList().forEach(jsonTable -> {
            String tableName = jsonTable.getTableName();
            Integer level = jsonTable.getLevel();
            TableTemplate tableTemplate = new TableTemplate();
            tableTemplate.setTableName(tableName);
            tableTemplate.setLevel(level.toString());
            // 遍历操作类型对应的列
            Map<OpType, List<String>> opTypeFieldSetMap = tableTemplate.getOpTypeFieldSetMap();
            jsonTable.getInsert().forEach(column -> MapUtils.getOrCreate(OpType.ADD, opTypeFieldSetMap, ArrayList::new).add(column.getName()));
            jsonTable.getUpdate().forEach(column -> MapUtils.getOrCreate(OpType.UPDATE, opTypeFieldSetMap, ArrayList::new).add(column.getName()));
            jsonTable.getDelete().forEach(column -> MapUtils.getOrCreate(OpType.DELETE, opTypeFieldSetMap, ArrayList::new).add(column.getName()));
            parseTemplate.tableTemplateMap.put(tableName, tableTemplate);
        });
        return parseTemplate;
    }
}
