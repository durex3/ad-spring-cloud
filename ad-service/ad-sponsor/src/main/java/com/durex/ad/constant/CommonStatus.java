package com.durex.ad.constant;

import lombok.Getter;

/**
 * 公共状态枚举类
 * @author gelong
 * @date 2019/11/5 23:41
 */
@Getter
public enum CommonStatus {

    /**
     * 有效状态
     */
    VALID(1, "有效状态"),

    /**
     * 无效状态
     */
    INVALID(0, "无限状态");

    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
