package com.durex.ad.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author gelong
 * @date 2019/12/30 21:23
 */
@Slf4j
public class DateUtils {

    /**
     * Tue Nov 20 04:42:27 GMT+08:00 2018
     * 格式化binlog时间格式
     * @param dateString 时间字符串
     * @return Date
     */
    public static Date parse(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat(
                "EEE MMM dd HH:mm:ss zzz yyyy",
                Locale.US
        );
        try {
            return org.apache.commons.lang3.time.DateUtils.addHours(
                    dateFormat.parse(dateString),
                    -8
            );
        } catch (ParseException e) {
            log.error("parse dateString error: {}", dateString);
        }
        return null;
    }
}
