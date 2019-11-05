package com.durex.ad.constant;

import lombok.Getter;

/**
 * 物料类型
 * @author gelong
 * @date 2019/11/6 0:09
 */
@Getter
public enum CreativeMetrialType {

    /**
     * 图片类型
     */
    JPG(1, "jpg"),
    BMP(2, "bmp"),

    /**
     * 视频类型
     */
    MP4(3, "mp4"),
    AVI(4, "avi"),

    /**
     * 文本类型
     */
    TXT(5, "txt");

    private int type;
    private String desc;

    CreativeMetrialType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
