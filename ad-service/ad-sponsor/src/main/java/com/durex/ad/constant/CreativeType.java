package com.durex.ad.constant;

import lombok.Getter;

/**
 * 创意类型枚举
 * @author gelong
 * @date 2019/11/6 0:08
 */
@Getter
public enum CreativeType {

    /**
     * 图片
     */
    IMAGE(1, "图片"),

    /**
     * 视频
     */
    VIDEO(2, "视频"),

    /**
     * 文本
     */
    TEXT(3, "文本");

    private int type;
    private String desc;

    CreativeType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
