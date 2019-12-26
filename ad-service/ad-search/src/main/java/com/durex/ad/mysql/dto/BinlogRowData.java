package com.durex.ad.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author gelong
 * @date 2019/12/24 20:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BinlogRowData {

    private TableTemplate table;
    private EventType eventType;
    private List<Map<String, String>> before;
    private List<Map<String, String>> after;
}
