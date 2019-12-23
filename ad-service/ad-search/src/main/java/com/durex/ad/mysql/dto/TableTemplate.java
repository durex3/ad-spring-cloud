package com.durex.ad.mysql.dto;

import com.durex.ad.mysql.constant.OpType;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author gelong
 * @date 2019/12/23 19:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableTemplate {

    private String tableName;
    private String level;
    private Map<OpType, List<String>> opTypeFieldSetMap = Maps.newHashMap();

    /**
     * 索引-字段名
     */
    private Map<Integer, String> posMap = Maps.newHashMap();
}
