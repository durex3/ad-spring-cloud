package com.durex.ad.mysql.listener;

import com.durex.ad.mysql.constant.Constant;
import com.durex.ad.mysql.constant.OpType;
import com.durex.ad.mysql.dto.BinlogRowData;
import com.durex.ad.mysql.dto.MySqlRowData;
import com.durex.ad.mysql.dto.TableTemplate;
import com.durex.ad.sender.ISender;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author gelong
 * @date 2019/12/26 22:12
 */
@Slf4j
@Component
public class IncrementListener implements Ilistener {

    @Resource
    private AggregationListener aggregationListener;
    @Resource
    private ISender sender;

    @PostConstruct
    @Override
    public void register() {
        log.info("IncrementListener register db and table info");
        Constant.table2Db.forEach((k, v) -> aggregationListener.register(v, k, this));
    }

    @Override
    public void onEvent(BinlogRowData eventData) {
        TableTemplate table = eventData.getTable();
        EventType eventType = eventData.getEventType();
        // 包装成最后需要投递的数据
        MySqlRowData rowData = new MySqlRowData();
        rowData.setTableName(table.getTableName());
        rowData.setLevel(table.getLevel());
        OpType opType = OpType.to(eventType);
        rowData.setOpType(opType);
        // 取出模板中该操作对应的字段列表
        List<String> fieldList = table.getOpTypeFieldSetMap().get(opType);
        if (CollectionUtils.isEmpty(fieldList)) {
            log.warn("{} not support for {}", opType, table.getTableName());
            return;
        }
        for (Map<String, String> afterMap : eventData.getAfter()) {
            Map<String, String> fieldValueMap = Maps.newHashMap();
            for (Map.Entry<String, String> entry : afterMap.entrySet()) {
                String colName = entry.getKey();
                String colValue = entry.getValue();
                fieldValueMap.put(colName, colValue);
            }
            rowData.getFieldValueMap().add(fieldValueMap);
        }
        sender.sender(rowData);
    }
}
