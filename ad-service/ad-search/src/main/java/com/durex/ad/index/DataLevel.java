package com.durex.ad.index;

import lombok.Getter;

/**
 * @author gelong
 * @date 2019/12/30 21:31
 */
@Getter
public enum DataLevel {

    /**
     * 第二层级
     */
    LEVEL2("2", "level 2"),

    /**
     * 第三层级
     */
    LEVEL3("3", "level 3"),

    /**
     * 第四层级
     */
    LEVEL4("4", "level 4");

    private String level;
    private String desc;

    DataLevel(String level, String desc) {
        this.level = level;
        this.desc = desc;
    }
}
