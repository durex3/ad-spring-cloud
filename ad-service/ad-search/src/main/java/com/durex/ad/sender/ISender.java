package com.durex.ad.sender;

import com.durex.ad.mysql.dto.MySqlRowData;

/**
 * @author gelong
 * @date 2019/12/26 22:10
 */
public interface ISender {

    /**
     * 投递数据
     * @param rowData 数据
     */
    void sender(MySqlRowData rowData);
}
