package com.durex.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author gelong
 * @date 2019/12/23 19:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonTable {

    private String tableName;
    private Integer level;
    private List<Column> insert;
    private List<Column> update;
    private List<Column> delete;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Column {
        private String name;
    }
}
