package com.gb.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对Date类型数据进行处理的工具类
 */
public class DateUtils {

    /**
     *  对指定的date对象进行格式化：yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static  String formatDateTime(Date date){
        SimpleDateFormat sdm = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return sdm.format(date);
    }

    /**
     *  对指定的date对象进行格式化：yyyy-MM-dd
     * @param date
     * @return
     */
    public static  String formatTime(Date date){
        SimpleDateFormat sdm = new SimpleDateFormat("yyyy-MM-dd");
        return sdm.format(date);
    }
}
