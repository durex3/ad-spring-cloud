package com.durex.ad.mysql.dto;

import com.durex.ad.mysql.constant.OpType;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

/**
 * @author gelong
 * @date 2019/12/26 21:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySqlRowData {

    private String tableName;
    private String level;
    private OpType opType;
    private List<Map<String, String>> fieldValueMap = Lists.newArrayList();
}
