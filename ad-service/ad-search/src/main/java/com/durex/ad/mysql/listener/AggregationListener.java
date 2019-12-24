package com.durex.ad.mysql.listener;

import com.durex.ad.mysql.TemplateHolder;
import com.durex.ad.mysql.dto.BinlogRowData;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

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
        return null;
    }
}
