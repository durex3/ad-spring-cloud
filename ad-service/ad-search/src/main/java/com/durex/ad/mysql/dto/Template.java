package com.durex.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author gelong
 * @date 2019/12/23 19:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Template {

    private String database;
    private List<JsonTable> tableList;
}
