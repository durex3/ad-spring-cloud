package com.durex.ad.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author gelong
 * @date 2019/11/6 0:47
 */
public class CommonUtils {

    public static String md5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }
}
