package org.wuhulala.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	private final static Logger log = LoggerFactory.getLogger(DateUtil.class);
	
    /** 年月日(无下划线) yyyyMMdd */
    public static final String DTYEAR  = "yyyy";

    /** 年月日(无下划线) yyyyMMdd */
    public static final String DTMONTH  = "yyyyMM";

    /** 年月日(无下划线) yyyyMMdd */
    public static final String DTSHORT  = "yyyyMMdd";

    /** 年月日时(无下划线) yyyyMMddHH */
    public static final String DTHOUR   = "yyyyMMddHH";

    /** 年月日时分(无下划线) yyyyMMddHHmm */
    public static final String DTMIDDLE = "yyyyMMddHHmm";

    /** 年月日时分秒(无下划线) yyyyMMddHHmmss */
    public static final String DTLONG   = "yyyyMMddHHmmss";
    public final static long ONE_DAY_SECONDS = 86400;

    /*
     * private static DateFormat dateFormat = null; private static DateFormat
     * longDateFormat = null; private static DateFormat dateWebFormat = null;
     */
    public final static String shortFormat = "yyyyMMdd";
    public final static String longFormat = "yyyyMMddHHmmss";
    public final static String webFormat = "yyyy-MM-dd";
    public final static String webMonthFormat = "yyyy-MM";
    public final static String webHourFormat = "yyyy-MM-dd HH";
    public final static String timeFormat = "HHmmss";
    public final static String monthFormat = "yyyyMM";
    public final static String chineseDtFormat = "yyyy年MM月dd日";
    public final static String newFormat = "yyyy-MM-dd HH:mm:ss";
    public final static String noSecondFormat = "yyyy-MM-dd HH:mm";
    public final static long ONE_DAY_MILL_SECONDS = 86400000;
    public final static String[] formatList = new String[]{
            shortFormat, longFormat, webFormat, chineseDtFormat, webMonthFormat, webHourFormat, timeFormat, monthFormat, newFormat, noSecondFormat
    };

    public static DateFormat getNewDateFormat(String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);

        df.setLenient(false);
        return df;
    }

    public static String format(Date date, String format) {
        if (date == null) {
            return null;
        }

        return new SimpleDateFormat(format).format(date);
    }

    public static Date parseAllDateFormat(String sData) {
        for (String f : formatList) {
            try {
                return parseDateNoTime(sData, f);
            } catch (Throwable t) {

            }
        }
        return null;
    }

    public static Date parseDateNoTime(String sDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(shortFormat);

        if ((sDate == null) || (sDate.length() < shortFormat.length())) {
            throw new ParseException("length too little", 0);
        }

        if (!StringUtils.isNumeric(sDate)) {
            throw new ParseException("not all digit", 0);
        }

        return dateFormat.parse(sDate);
    }

    public static Date parseDateNoTime(String sDate, String format) throws ParseException {
        if (StringUtils.isBlank(format)) {
            throw new ParseException("Null format. ", 0);
        }

        DateFormat dateFormat = new SimpleDateFormat(format);

        if ((sDate == null) || (sDate.length() < format.length())) {
            throw new ParseException("length too little", 0);
        }

        return dateFormat.parse(sDate);
    }

    public static Date parseDateNoTimeWithDelimit(String sDate, String delimit)
            throws ParseException {
        sDate = sDate.replaceAll(delimit, "");

        DateFormat dateFormat = new SimpleDateFormat(shortFormat);

        if ((sDate == null) || (sDate.length() != shortFormat.length())) {
            throw new ParseException("length not match", 0);
        }

        return dateFormat.parse(sDate);
    }

    public static Date parseDateLongFormat(String sDate) {
        DateFormat dateFormat = new SimpleDateFormat(longFormat);
        Date d = null;

        if ((sDate != null) && (sDate.length() == longFormat.length())) {
            try {
                d = dateFormat.parse(sDate);
            } catch (ParseException ex) {
                return null;
            }
        }

        return d;
    }

    public static Date parseDateNewFormat(String sDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(newFormat);
        Date d = dateFormat.parse(sDate);
        return d;
    }

    public static Date parseDateWebFormat(String sDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(webFormat);
        Date d = dateFormat.parse(sDate);
        return d;
    }
    /**
     * 计算当前时间几小时之后的时间
     *
     * @param date
     * @param hours
     * @return
     */
    public static Date addHours(Date date, long hours) {
        return addMinutes(date, hours * 60);
    }

    /**
     * 计算当前时间几分钟之后的时间
     *
     * @param date
     * @param minutes
     * @return
     */
    public static Date addMinutes(Date date, long minutes) {
        return addSeconds(date, minutes * 60);
    }

    /**
     * @param date1
     * @param secs
     * @return
     */

    public static Date addSeconds(Date date1, long secs) {
        return new Date(date1.getTime() + (secs * 1000));
    }

    /**
     * 判断输入的字符串是否为合法的小时
     *
     * @param hourStr
     * @return true/false
     */
    public static boolean isValidHour(String hourStr) {
        if (!StringUtils.isEmpty(hourStr) && StringUtils.isNumeric(hourStr)) {
            int hour = new Integer(hourStr).intValue();

            if ((hour >= 0) && (hour <= 23)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断输入的字符串是否为合法的分或秒
     *
     * @param minuteStr
     * @return true/false
     */
    public static boolean isValidMinuteOrSecond(String str) {
        if (!StringUtils.isEmpty(str) && StringUtils.isNumeric(str)) {
            int hour = new Integer(str).intValue();

            if ((hour >= 0) && (hour <= 59)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 取得新的日期
     *
     * @param date1 日期
     * @param days  天数
     * @return 新的日期
     */
    public static Date addDays(Date date1, long days) {
        return addSeconds(date1, days * ONE_DAY_SECONDS);
    }

    public static String getTomorrowDateString(String sDate) throws ParseException {
        Date aDate = parseDateNoTime(sDate);

        aDate = addSeconds(aDate, ONE_DAY_SECONDS);

        return getDateString(aDate);
    }

    public static String getTomorrowWebDate(String sDate) throws ParseException {
        String tomorrowShortDate = getTomorrowDateString(convertWeb2ShortFormat(sDate));

        return convert2WebFormat(tomorrowShortDate);
    }

    public static String getLongDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(longFormat);

        return getDateString(date, dateFormat);
    }

    public static String getNewFormatDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(newFormat);
        return getDateString(date, dateFormat);
    }

    public static String getDateString(Date date, DateFormat dateFormat) {
        if (date == null || dateFormat == null) {
            return null;
        }

        return dateFormat.format(date);
    }

    public static String getYesterDayDateString(String sDate) throws ParseException {
        Date aDate = parseDateNoTime(sDate);

        aDate = addSeconds(aDate, -ONE_DAY_SECONDS);

        return getDateString(aDate);
    }

    /**
     * @return 当天的时间格式化为"yyyyMMdd"
     */
    public static String getDateString(Date date) {
        DateFormat df = getNewDateFormat(shortFormat);

        return df.format(date);
    }

    public static String getWebDateString(Date date) {
        DateFormat dateFormat = getNewDateFormat(webFormat);

        return getDateString(date, dateFormat);
    }

    public static String getNoSecondDateString(Date date) {
        DateFormat dateFormat = getNewDateFormat(noSecondFormat);

        return getDateString(date, dateFormat);
    }

    /**
     * 取得“X年X月X日”的日期格式
     *
     * @param date
     * @return
     */
    public static String getChineseDateString(Date date) {
        DateFormat dateFormat = getNewDateFormat(chineseDtFormat);

        return getDateString(date, dateFormat);
    }

    public static String getTodayString() {
        DateFormat dateFormat = getNewDateFormat(shortFormat);

        return getDateString(new Date(), dateFormat);
    }

    public static String getTimeString(Date date) {
        DateFormat dateFormat = getNewDateFormat(timeFormat);

        return getDateString(date, dateFormat);
    }

    public static String getBeforeDayString(int days) {
        Date date = new Date(System.currentTimeMillis() - (ONE_DAY_MILL_SECONDS * days));
        DateFormat dateFormat = getNewDateFormat(shortFormat);

        return getDateString(date, dateFormat);
    }

    /**
     * 取得两个日期间隔秒数（日期1-日期2）
     *
     * @param one 日期1
     * @param two 日期2
     * @return 间隔秒数
     */
    public static long getDiffSeconds(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();

        sysDate.setTime(one);

        Calendar failDate = new GregorianCalendar();

        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 1000;
    }

    public static long getDiffMinutes(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();

        sysDate.setTime(one);

        Calendar failDate = new GregorianCalendar();

        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (60 * 1000);
    }

    /**
     * 取得两个日期的间隔天数
     *
     * @param one
     * @param two
     * @return 间隔天数
     */
    public static long getDiffDays(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();

        sysDate.setTime(one);

        Calendar failDate = new GregorianCalendar();

        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (24 * 60 * 60 * 1000);
    }

    public static String getBeforeDayString(String dateString, int days) {
        Date date;
        DateFormat df = getNewDateFormat(shortFormat);

        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            date = new Date();
        }

        date = new Date(date.getTime() - (ONE_DAY_MILL_SECONDS * days));

        return df.format(date);
    }

    public static boolean isValidShortDateFormat(String strDate) {
        if (strDate.length() != shortFormat.length()) {
            return false;
        }

        try {
            Integer.parseInt(strDate); //---- 避免日期中输入非数字 ----
        } catch (Exception NumberFormatException) {
            return false;
        }

        DateFormat df = getNewDateFormat(shortFormat);

        try {
            df.parse(strDate);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    public static boolean isValidShortDateFormat(String strDate, String delimiter) {
        String temp = strDate.replaceAll(delimiter, "");

        return isValidShortDateFormat(temp);
    }

    /**
     * 判断表示时间的字符是否为符合yyyyMMddHHmmss格式
     *
     * @param strDate
     * @return
     */
    public static boolean isValidLongDateFormat(String strDate) {
        if (strDate.length() != longFormat.length()) {
            return false;
        }

        try {
            Long.parseLong(strDate); //---- 避免日期中输入非数字 ----
        } catch (Exception NumberFormatException) {
            return false;
        }

        DateFormat df = getNewDateFormat(longFormat);

        try {
            df.parse(strDate);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    /**
     * 判断表示时间的字符是否为符合yyyyMMddHHmmss格式
     *
     * @param strDate
     * @param delimiter
     * @return
     */
    public static boolean isValidLongDateFormat(String strDate, String delimiter) {
        String temp = strDate.replaceAll(delimiter, "");

        return isValidLongDateFormat(temp);
    }

    public static String getShortDateString(String strDate) {
        return getShortDateString(strDate, "-|/");
    }

    public static String getShortDateString(String strDate, String delimiter) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        String temp = strDate.replaceAll(delimiter, "");

        if (isValidShortDateFormat(temp)) {
            return temp;
        }

        return null;
    }

    public static String getShortFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        Date dt = new Date();

        cal.setTime(dt);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        DateFormat df = getNewDateFormat(shortFormat);

        return df.format(cal.getTime());
    }

    public static String getWebTodayString() {
        DateFormat df = getNewDateFormat(webFormat);

        return df.format(new Date());
    }

    public static String getWebFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        Date dt = new Date();

        cal.setTime(dt);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        DateFormat df = getNewDateFormat(webFormat);

        return df.format(cal.getTime());
    }

    public static String convert(String dateString, DateFormat formatIn, DateFormat formatOut) {
        try {
            Date date = formatIn.parse(dateString);

            return formatOut.format(date);
        } catch (ParseException e) {
            log.warn("convert() --- orign date error: " + dateString);
            return "";
        }
    }

    public static String convertWeb2ShortFormat(String dateString) {
        DateFormat df1 = getNewDateFormat(webFormat);
        DateFormat df2 = getNewDateFormat(shortFormat);

        return convert(dateString, df1, df2);
    }

    public static String convert2WebFormat(String dateString) {
        dateString = StringUtils.defaultString(dateString);
        DateFormat df1 = getNewDateFormat(shortFormat);
        DateFormat df2 = getNewDateFormat(webFormat);

        return convert(dateString, df1, df2);
    }

    public static String convert2ChineseDtFormat(String dateString) {
        DateFormat df1 = getNewDateFormat(shortFormat);
        DateFormat df2 = getNewDateFormat(chineseDtFormat);

        return convert(dateString, df1, df2);
    }

    public static String convertFromWebFormat(String dateString) {
        DateFormat df1 = getNewDateFormat(shortFormat);
        DateFormat df2 = getNewDateFormat(webFormat);

        return convert(dateString, df2, df1);
    }

    public static boolean webDateNotLessThan(String date1, String date2) {
        DateFormat df = getNewDateFormat(webFormat);

        return dateNotLessThan(date1, date2, df);
    }

    /**
     * @param date1
     * @param date2
     * @param dateWebFormat2
     * @return
     */
    public static boolean dateNotLessThan(String date1, String date2, DateFormat format) {
        try {
            Date d1 = format.parse(date1);
            Date d2 = format.parse(date2);

            if (d1.before(d2)) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            log.debug("dateNotLessThan() --- ParseException(" + date1 + ", " + date2 + ")");
            return false;
        }
    }

    /**
     * @param date1
     * @param date2
     * @return
     */
    public static boolean dateLessThan(Date d1, Date d2) {
        try {
            if (d1.before(d2)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.debug("dateLessThan() ---Exception(" + d1 + ", " + d2 + ")");
            return false;
        }
    }
    
    public static String getEmailDate(Date today) {
        String todayStr;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");

        todayStr = sdf.format(today);
        return todayStr;
    }

    public static String getSmsDate(Date today) {
        String todayStr;
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH:mm");

        todayStr = sdf.format(today);
        return todayStr;
    }

    public static String formatTimeRange(Date startDate, Date endDate, String format) {
        if ((endDate == null) || (startDate == null)) {
            return null;
        }

        String rt = null;
        long range = endDate.getTime() - startDate.getTime();
        long day = range / DateUtils.MILLIS_PER_DAY;
        long hour = (range % DateUtils.MILLIS_PER_DAY) / DateUtils.MILLIS_PER_HOUR;
        long minute = (range % DateUtils.MILLIS_PER_HOUR) / DateUtils.MILLIS_PER_MINUTE;

        if (range < 0) {
            day = 0;
            hour = 0;
            minute = 0;
        }

        rt = format.replaceAll("dd", String.valueOf(day));
        rt = rt.replaceAll("hh", String.valueOf(hour));
        rt = rt.replaceAll("mm", String.valueOf(minute));

        return rt;
    }

    public static String formatMonth(Date date) {
        if (date == null) {
            return null;
        }

        return new SimpleDateFormat(monthFormat).format(date);
    }

    /**
     * 获取系统日期的前一天日期，返回Date
     *
     * @return
     */
    public static Date getBeforeDate() {
        Date date = new Date();

        return new Date(date.getTime() - (ONE_DAY_MILL_SECONDS));
    }

    /**
     * 获得指定时间当天起点时间
     *
     * @param date
     * @return
     */
    public static Date getDayBegin(Date date) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        df.setLenient(false);

        String dateString = df.format(date);

        try {
            return df.parse(dateString);
        } catch (ParseException e) {
            return date;
        }
    }

    /**
     * 获得指定时间当天最末时间
     *
     * @param date
     * @return
     */
    public static Date getDayEnd(Date date) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        df.setLenient(false);

        String dateString = df.format(date);
        dateString += "235959";

        try {
            return parseDateLongFormat(dateString);
        } catch (Exception e) {
            return date;
        }
    }

    /**
     * 判断参date上min分钟后，是否小于当前时间
     *
     * @param date
     * @param min
     * @return
     */
    public static boolean dateLessThanNowAddMin(Date date, long min) {
        return addMinutes(date, min).before(new Date());

    }

    /**
     * 是否在现在时间之前
     *
     * @param date
     * @return
     */
    public static boolean isBeforeNow(Date date) {
        if (date == null)
            return false;
        return date.compareTo(new Date()) < 0;
    }

    /**
     * 是否有效
     *
     * @param requestTime 请求时间
     * @param effectTime  生效时间
     * @param expiredTime 失效时间
     * @return
     */
    public static boolean isValidate(Date requestTime, Date effectTime, Date expiredTime) {
        if (requestTime == null || effectTime == null || expiredTime == null) {
            return false;
        }
        return effectTime.compareTo(requestTime) <= 0 && expiredTime.compareTo(requestTime) >= 0;
    }

    public static void main(String[] args) throws ParseException {
        System.out.println("[" + convert2WebFormat(null) + "]");
        Date d1 = DateUtil.parseDateNewFormat("2013-07-09 12:12:12");
        if(DateUtil.dateLessThan(d1, DateUtil.getCurrentTS())){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
    }

    public static Date parseNoSecondFormat(String sDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(noSecondFormat);

        if ((sDate == null) || (sDate.length() < noSecondFormat.length())) {
            throw new ParseException("length too little", 0);
        }

        if (!StringUtils.isNumeric(sDate)) {
            throw new ParseException("not all digit", 0);
        }

        return dateFormat.parse(sDate);
    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat(webFormat);
        return sdf.format(date1).equals(sdf.format(date2));
    }

    /**
     * 获取系统当前时间
     *
     * @return
     */
    public static Date getCurrentTS() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 获取系统当前时间
     *
     * @return
     */
    public static Date getWeekBefore() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 获取当前时间的前几天或者后几天的日期
     *
     * @param days
     * @return
     */
    public static Date getDateNearCurrent(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        Date date = calendar.getTime();
        return date;

    }

    /**
     * 某一个月第一天
     *
     * @param date months 前后第几个月
     * @return
     * @throws ParseException
     */
    public static Date getFirstday_Month(Date date, int months) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(webFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        Date theDate = calendar.getTime();

        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        day_first = str.toString();
        return parseDateNewFormat(day_first);
    }

    /**
     * 某一个月最后一天
     *
     * @param date months 前后第几个月
     * @return
     * @throws ParseException
     */
    public static Date getLastday_Month(Date date, int months) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);

        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
        day_last = endStr.toString();
        return parseDateNewFormat(day_last);
    }
    
    public static Date stringToDate(String stringDate, String format) {
        if (stringDate == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(format).parse(stringDate);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static Date getFirstDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        cal.setMinimalDaysInFirstWeek(1);
        return cal.getTime();
    }

    public static int getWeekOfYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.setMinimalDaysInFirstWeek(1);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }
}
