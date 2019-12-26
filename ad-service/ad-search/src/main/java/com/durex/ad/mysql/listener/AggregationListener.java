package com.durex.ad.mysql.listener;

import com.durex.ad.mysql.TemplateHolder;
import com.durex.ad.mysql.dto.BinlogRowData;
import com.durex.ad.mysql.dto.TableTemplate;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gelong
 * @date 2019/12/24 20:25
 */
@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private String dbName;
    private String tableName;
    private Map<String, Ilistener> listenerMap;
    @Resource
    private TemplateHolder templateHolder;

    private String genKey(String dbName, String tableName) {
        return dbName + ":" + tableName;
    }

    public void register(String dbName, String tableName, Ilistener ilistener) {
        log.info("register: {}-{}", dbName, tableName);
        this.listenerMap.put(genKey(dbName, tableName), ilistener);
    }

    @Override
    public void onEvent(Event event) {
        EventType eventType = event.getHeader().getEventType();
        log.debug("event type: {}", eventType);
        if (eventType == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            this.dbName = data.getDatabase();
            this.tableName = data.getTable();
            return;
        }
        if (!EventType.isUpdate(eventType) && !EventType.isWrite(eventType) && !EventType.isDelete(eventType)) {
            return;
        }
        if (StringUtils.isEmpty(this.dbName) || StringUtils.isEmpty(this.tableName)) {
            log.error("no meta data event");
        }
        // 找出监听器
        String key = genKey(this.dbName, this.tableName);
        Ilistener ilistener = this.listenerMap.get(key);
        if (ilistener == null) {
            log.debug("skip: {}", key);
            return;
        }
        log.info("trigger event: {}", eventType.name());
        try {
            BinlogRowData rowData = buildRowData(event.getData());
            if (rowData == null) {
                return;
            }
            rowData.setEventType(eventType);
            ilistener.onEvent(rowData);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            this.dbName = "";
            this.tableName = "";
        }
    }

    private BinlogRowData buildRowData(EventData eventData) {
        TableTemplate table = templateHolder.getTable(tableName);
        if (table == null) {
            log.warn("table {} not found", tableName);
            return null;
        }
        List<Map<String, String>> afterMapList = Lists.newArrayList();
        for (Serializable[] after : getAfterValues(eventData)) {
            Map<String, String> afterMap = Maps.newHashMap();
            int length = after.length;
            for (int i = 0; i < length; i++) {
                // 取出当前位置对应的列名
                String colName = table.getPosMap().get(i);
                // 如果没有则跳过
                if (StringUtils.isEmpty(colName)) {
                    log.debug("ignore position: {}", i);
                    continue;
                }
                String colValue = after[i].toString();
                afterMap.put(colName, colValue);
            }
            afterMapList.add(afterMap);
        }
        return new BinlogRowData(table, null, null, afterMapList);
    }

    private List<Serializable[]> getAfterValues(EventData eventData) {
        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData)eventData).getRows();
        }
        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData)eventData).getRows().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        }
        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData)eventData).getRows();
        }
        return Lists.newArrayList();
    }
}
