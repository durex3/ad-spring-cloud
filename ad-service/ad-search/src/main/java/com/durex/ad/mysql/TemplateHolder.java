package com.durex.ad.mysql;

import com.alibaba.fastjson.JSON;
import com.durex.ad.mysql.constant.OpType;
import com.durex.ad.mysql.dto.ParseTemplate;
import com.durex.ad.mysql.dto.TableTemplate;
import com.durex.ad.mysql.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author gelong
 * @date 2019/12/23 21:35
 */
@Slf4j
@Component
public class TemplateHolder {

    private ParseTemplate parseTemplate;
    @Resource
    private JdbcTemplate jdbcTemplate;

    private final String SQL_SCHEMA = "select table_schema, table_name, " +
            "column_name, ordinal_position from information_schema.columns " +
            "where table_schema = ? and table_name = ?";

    @PostConstruct
    private void init() {
        loadJson("template.json");
    }

    public TableTemplate getTable(String tableName) {
        return parseTemplate.getTableTemplateMap().get(tableName);
    }

    private void loadMeta() {
        for (Map.Entry<String, TableTemplate> entry : parseTemplate.getTableTemplateMap().entrySet()) {
            TableTemplate tableTemplate = entry.getValue();
            List<String> insertFields = tableTemplate.getOpTypeFieldSetMap().get(
                    OpType.ADD
            );
            List<String> updateFields = tableTemplate.getOpTypeFieldSetMap().get(
                    OpType.UPDATE
            );
            List<String> deleteFields = tableTemplate.getOpTypeFieldSetMap().get(
                    OpType.DELETE
            );
            jdbcTemplate.query(SQL_SCHEMA, new Object[] {
                    parseTemplate.getDatabase(),
                    tableTemplate.getTableName()
            }, (rs, i) -> {
                int pos = rs.getInt("ORDINAL_POSITION");
                String colName = rs.getString("COLUMN_NAME");
                boolean existed = (updateFields != null && updateFields.contains(colName))
                        || (insertFields != null && insertFields.contains(colName))
                        || (deleteFields != null && deleteFields.contains(colName));
                if (existed) {
                    tableTemplate.getPosMap().put(pos - 1, colName);
                }
                return null;
            });
        }
    }

    private void loadJson(String path) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        if (inputStream == null) {
            throw new RuntimeException("path is not exists");
        }
        try {
            Template template = JSON.parseObject(
                    inputStream,
                    Charset.defaultCharset(),
                    Template.class
            );
            this.parseTemplate = ParseTemplate.parse(template);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("fail to parse json file");
        }
    }
}
