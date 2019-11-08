package com.durex.ad.utils;

import com.durex.ad.exception.AdException;

import java.text.ParseException;
import java.util.Date;

/**
 * 日期工具类
 * @author gelong
 * @date 2019/11/8 22:58
 */
public class DateUtils {

    private DateUtils() {

    }

    private static String[] parsePatterns = {
        "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    public static Date parseStringDate(String dateStr) throws AdException {
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, parsePatterns);
        } catch (ParseException e) {
            throw new AdException(e.getMessage());
        }
    }
}
