package com.cnh.frame.wraps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/9/22
 */
public class DateWrap {

    private final static Logger LOGGER = LoggerFactory.getLogger(DateWrap.class);

    private final static Calendar CALENDAR = Calendar.getInstance();

    public final static String FORMAT_YEAR = "yyyy";

    public final static String FORMAT_MONTH = "MM";

    public final static String FORMAT_DATE = "dd";

    public final static String FORMAT_HOUR = "HH";

    public final static String FORMAT_MINUTE = "mm";

    public final static String FORMAT_SECOND = "ss";

    public final static String DATE_FORMAT = "yyyy-MM-dd";

    public final static String TIME_FORMAT = "HH:mm:ss";

    public final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";


    public static void main(String[] args) {
        Date begin = getFirstDayOfWeek(2016, 1);
        Date end = getLastDayOfWeek(2016, 1);

        System.out.println(format(begin));      // 2016-01-04 00:00:00
        System.out.println(format(end));        // 2016-01-10 00:00:00
    }


    /**
     * 取得日期及时间，并格式化返回
     * @param date
     * @return
     */
    public static String getDateTime(Date date) {
        return format(date, DATETIME_FORMAT);
    }

    /**
     * 取得日期，并格式化返回
     * @param date
     * @return
     */
    public static String getDate(Date date) {
        return format(date, DATE_FORMAT);
    }

    /**
     * 取得时间，并格式化返回
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        return format(date, TIME_FORMAT);
    }

    /**
     * 取得日期的年份
     * @param date
     * @return
     */
    public static String getYear(Date date) {
        CALENDAR.clear();
        CALENDAR.setTime(date);
        return String.valueOf(CALENDAR.get(Calendar.YEAR));
    }

    /**
     * 取得日期的月份
     * @param date
     * @return
     */
    public static String getMonth(Date date) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.applyPattern("00;00");
        CALENDAR.clear();
        CALENDAR.setTime(date);
        return decimalFormat.format(CALENDAR.get(Calendar.MONTH) + 1);
    }

    /**
     * 取得日期的月份中天数
     * @param date
     * @return
     */
    public static String getDayOfMonth(Date date) {
        CALENDAR.clear();
        CALENDAR.setTime(date);
        return String.valueOf(CALENDAR.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 取得日期星期中的天数
     * @param date
     * @return
     */
    public static String getDayOfWeek(Date date) {
        CALENDAR.clear();
        CALENDAR.setTime(date);
        return String.valueOf(CALENDAR.get(Calendar.DAY_OF_WEEK));
    }

    /**
     * 取得日期是年中第几周
     * @param date
     * @param firstDayOfWeek
     * @param minimalDaysInFirstWeek
     * @return
     */
    public static int getWeekOfYear(Date date, int firstDayOfWeek, int minimalDaysInFirstWeek) {
        CALENDAR.clear();
        CALENDAR.setFirstDayOfWeek(firstDayOfWeek);
        CALENDAR.setMinimalDaysInFirstWeek(minimalDaysInFirstWeek);
        CALENDAR.setTime(date);
        return CALENDAR.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 取得日期是年中第几周
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        return getWeekOfYear(date, Calendar.MONDAY, 7);
    }

    /**
     * 计算某年的总周数
     * @param year
     * @return
     */
    public static int getWeekCountOfYear(int year) {
        CALENDAR.clear();
        CALENDAR.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        return getWeekOfYear(CALENDAR.getTime());
    }

    /**
     * 取得某年某周的第一天
     * @param year
     * @param week
     * @return
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        CALENDAR.clear();
        CALENDAR.set(Calendar.YEAR, year);
        CALENDAR.set(Calendar.MONTH, Calendar.JANUARY);
        CALENDAR.set(Calendar.DATE, 1);
        CALENDAR.add(Calendar.DATE, week * 7);
        return getFirstDayOfWeek(CALENDAR.getTime());
    }

    /**
     * 取得指定日期所在周的第一天
     * @param date
     * @param firstDayOfWeek
     * @return
     */
    public static Date getFirstDayOfWeek(Date date, int firstDayOfWeek) {
        CALENDAR.clear();
        CALENDAR.setFirstDayOfWeek(firstDayOfWeek);
        CALENDAR.setTime(date);
        CALENDAR.set(Calendar.DAY_OF_WEEK, CALENDAR.getFirstDayOfWeek());
        return CALENDAR.getTime();
    }

    /**
     *  取得某年某周的最一天
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        return getFirstDayOfWeek(date, Calendar.MONDAY);
    }

    /**
     * 取得指定日期所在周的最后一天
     * @param year
     * @param week
     * @return
     */
    public static Date getLastDayOfWeek(int year, int week) {
        CALENDAR.clear();
        CALENDAR.set(Calendar.YEAR, year);
        CALENDAR.set(Calendar.MONTH, Calendar.JANUARY);
        CALENDAR.set(Calendar.DATE, 1);
        CALENDAR.add(Calendar.DATE, week * 7);
        return getLastDayOfWeek(CALENDAR.getTime());
    }

    /**
     * 取得指定改日期所在周的最后一天
     * @param date
     * @param firstDayOfWeek
     * @return
     */
    public static Date getLastDayOfWeek(Date date, int firstDayOfWeek) {
        CALENDAR.clear();
        CALENDAR.setFirstDayOfWeek(firstDayOfWeek);
        CALENDAR.setTime(date);
        CALENDAR.set(Calendar.DAY_OF_WEEK, CALENDAR.getFirstDayOfWeek() + 6);
        return CALENDAR.getTime();
    }

    /**
     * 取得指定日期所在周的最后一天
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        return getLastDayOfWeek(date, Calendar.MONDAY);
    }

    /**
     * 比较两个日期相差的天数
     * @param dateStr1
     * @param dateStr2
     * @return
     */
    public static int getDayGapsBetweenDates(String dateStr1, String dateStr2) {
        Date date1 = parse(dateStr1, DATETIME_FORMAT);
        Date date2 = parse(dateStr2, DATETIME_FORMAT);
        long l = date1.getTime() - date2.getTime();
        return (int)(l/(24 * 60 * 60 * 1000));
    }

    /**
     * 比较两个日期相差的月份数
     * @param dateStr1
     * @param dateStr2
     * @return
     */
    public static int getMonthGapsBetweenDates(String dateStr1, String dateStr2) {
        int month1, month2;
        Date date1 = parse(dateStr1, DATETIME_FORMAT);
        Date date2 = parse(dateStr2, DATETIME_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        month1 = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH);
        calendar.clear();
        calendar.setTime(date2);
        month2 = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH);
        return month1 - month2;
    }

    /**
     * 比较两个日期相差的年数
     * @param dateStr1
     * @param dateStr2
     * @return
     */
    public static int getYearGapsBetweenDates(String dateStr1, String dateStr2) {
        int year1, year2;
        Date date1 = parse(dateStr1, DATETIME_FORMAT);
        Date date2 = parse(dateStr2, DATETIME_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        year1 = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.setTime(date2);
        year2 = calendar.get(Calendar.YEAR);
        return year1 - year2;
    }

    /**
     * 取得GregorianCalendar实例
     * @param dateStr
     * @return
     */
    public static GregorianCalendar getGCalendar(String dateStr) {
        Date date = parse(dateStr, DATETIME_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new GregorianCalendar(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
    }

    public static GregorianCalendar getGCalendar(String dateStr, String format) {
        Date date = parse(dateStr, format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new GregorianCalendar(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
    }

    /**
     * 取得GregorianCalendar实例操作日期添加，并格式化返回
     * @param dateStr
     * @param value
     * @param gCalendarType
     * @return
     */
    public static String addGCalendar(String dateStr, int value, int gCalendarType) {
        return addGCalendar(dateStr, value, gCalendarType, DATETIME_FORMAT);
    }

    /**
     * 取得GregorianCalendar实例操作日期添加，并格式化返回
     * @param dateStr
     * @param value
     * @param gCalendarType
     * @return
     */
    public static String addGCalendar(String dateStr, int value, int gCalendarType, String format) {
        GregorianCalendar gregorianCalendar = getGCalendar(dateStr, format);
        gregorianCalendar.add(gCalendarType, value);
        return format(gregorianCalendar.getTime(), format);
    }

    /**
     * 返回日期加X秒后的日期
     * @param dateStr
     * @param value
     * @return
     */
    public static String addSecond(String dateStr, int value) {
        return addGCalendar(dateStr, value, GregorianCalendar.SECOND);
    }

    public static String addSecond(String dateStr, int value, String format) {
        return addGCalendar(dateStr, value, GregorianCalendar.SECOND, format);
    }

    /**
     * 返回日期加X分后的日期
     * @param dateStr
     * @param value
     * @return
     */
    public static String addMinute(String dateStr, int value) {
        return addGCalendar(dateStr, value, GregorianCalendar.MINUTE);
    }

    public static String addMinute(String dateStr, int value, String format) {
        return addGCalendar(dateStr, value, GregorianCalendar.MINUTE, format);
    }

    /**
     * 返回日期加X小时后的日期
     * @param dateStr
     * @param value
     * @return
     */
    public static String addHour(String dateStr, int value) {
        return addGCalendar(dateStr, value, GregorianCalendar.HOUR);
    }

    public static String addHour(String dateStr, int value, String format) {
        return addGCalendar(dateStr, value, GregorianCalendar.HOUR, format);
    }

    /**
     * 返回日期加X天后的日期
     * @param dateStr
     * @param value
     * @return
     */
    public static String addDay(String dateStr, int value) {
        return addGCalendar(dateStr, value, GregorianCalendar.DATE);
    }

    public static String addDay(String dateStr, int value, String format) {
        return addGCalendar(dateStr, value, GregorianCalendar.DATE, format);
    }

    /**
     * 返回日期加X月后的日期
     * @param dateStr
     * @param value
     * @return
     */
    public static String addMonth(String dateStr, int value) {
        return addGCalendar(dateStr, value, GregorianCalendar.MONTH);
    }

    /**
     * 返回日期加X年后的日期
     * @param dateStr
     * @param value
     * @return
     */
    public static String addYear(String dateStr, int value) {
        return addGCalendar(dateStr, value, GregorianCalendar.YEAR);
    }

    public static String addYear(String dateStr, int value, String format) {
        return addGCalendar(dateStr, value, GregorianCalendar.YEAR, format);
    }

    /**
     * 日期字符串装为日期
     * @param dateStr
     * @param format
     * @return
     */
    public static Date parse(String dateStr, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 默认格式转换日期
     * @param dateStr
     * @return
     */
    public static Date parse(String dateStr) {
        return parse(dateStr, DATETIME_FORMAT);
    }

    /**
     * 日期格式化
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 默认格式格式化日期
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, DATETIME_FORMAT);
    }

    /**
     * 年份是否为闰年
     * @param year
     * @return
     */
    public static boolean isLeap (int year) {
        return year %4 == 0 && year % 100 != 0 || year %400 == 0;
    }
}
