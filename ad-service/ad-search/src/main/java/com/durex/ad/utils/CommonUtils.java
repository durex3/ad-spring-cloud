package com.durex.ad.utils;

/**
 * @author gelong
 * @date 2019/12/3 20:16
 */
public class CommonUtils {

    public static String stringConcat(String... args) {
        StringBuilder result = new StringBuilder();
        for (String arg : args) {
            result.append(arg);
            result.append("-");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
}
