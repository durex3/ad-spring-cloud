package com.durex.ad;

import lombok.Getter;

/**
 * @author gelong
 * @date 2020/2/10 19:00
 */
@Getter
public enum  CommonStatus {

    VALID(1, "有效状态"),
    INVALID(0, "无效状态");

    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
