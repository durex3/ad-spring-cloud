package com.durex.ad.mysql.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * @author gelong
 * @date 2019/12/3 19:37
 */
public enum OpType {

    /**
     * 添加
     */
    ADD,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 其他
     */
    OTHER;

    public static OpType to(EventType eventType) {
        if (EventType.isWrite(eventType)) {
            return ADD;
        } else if (EventType.isUpdate(eventType)) {
            return UPDATE;
        } else if (EventType.isDelete(eventType)) {
            return DELETE;
        } else {
            return OTHER;
        }
    }
}
