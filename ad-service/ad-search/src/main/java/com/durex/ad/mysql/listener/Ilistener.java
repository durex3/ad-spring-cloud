package com.durex.ad.mysql.listener;

import com.durex.ad.mysql.dto.BinlogRowData;

/**
 * @author gelong
 * @date 2019/12/24 20:22
 */
public interface Ilistener {

    /**
     * 不同数据处理
     */
    void register();

    /**
     * 监听事件处理
     * @param eventData 数据
     */
    void onEvent(BinlogRowData eventData);
}
