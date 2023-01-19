package org.itstack.navice.chat.ui.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * TODO
 * yyyy:MM:dd HH:mm:ss
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/29 19:56
 */
public class DateUtil {

    /**
     * 将给定的日期时间格式化
     * 如果日期时间是当天，则格式化为HH:mm
     * 如果日期时间不是当天，则格式化为yyyy/MM/da
     * @author hourui
     * @date 2022/11/29 19:57
     * @return java.lang.String
     */
    public static String formatDate(Date date){
        boolean isToday =  isToday(date);
        if(isToday){
            return new SimpleDateFormat("HH:mm").format(date);
        }
        return new SimpleDateFormat("yyyy/MM/dd").format(date);
    }

    private static boolean isToday(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        Calendar c2 = Calendar.getInstance();
        c1.setTime(new Date());
        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        return year1 == year2 && month1 == month2 && day1 == day2;
    }
}
