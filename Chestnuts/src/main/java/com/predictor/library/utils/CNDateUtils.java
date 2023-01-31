package com.predictor.library.utils;


import static com.predictor.library.utils.CNStringUtils.DAY;
import static com.predictor.library.utils.CNStringUtils.HOUR;
import static com.predictor.library.utils.CNStringUtils.MIN;
import static com.predictor.library.utils.CNStringUtils.MSEC;
import static com.predictor.library.utils.CNStringUtils.SEC;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class CNDateUtils {

    public static String getTodayDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        return format.format(new Date());
    }

    /**
     * 掉此方法输入所要转换的时间输入例如（"2014年06月14日16时09分00秒"）返回时间戳
     *
     * @param time
     * @return
     */
    public String data(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    public static String getTodayDateTimes() {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日",
                Locale.getDefault());
        return format.format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime_Today() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return sdf.format(new Date());
    }

    /**
     * 调此方法输入所要转换的时间输入例如（"2014-06-14-16-09-00"）返回时间戳
     *
     * @param time
     * @return
     */
    public static String dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    public static String getTimestamp(String time, String type) {
        SimpleDateFormat sdr = new SimpleDateFormat(type, Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     *
     * @param time
     * @return
     */
    public static String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14  16:09:00"）
     *
     * @param time
     * @return
     */
    public static String timedate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16:09"）
     *
     * @param time
     * @return
     */
    public static String timet(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * @param
     * @return
     */
    public static String timeslash(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy/MM/dd,HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * @param
     * @return
     */
    public static String timeslashData(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy/MM/dd");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        //      int i = Integer.parseInt(time);
        String times = sdr.format(new Date(lcc * 1000L));
        return times;

    }

    /**
     * @param
     * @return
     */
    public static String timeMinute(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    public static String tim(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyyMMdd HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

    public static String time(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

    // 调用此方法输入所要转换的时间戳例如（1402733340）输出（"2014年06月14日16时09分00秒"）
    public static String times(long timeStamp) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM月dd日  #  HH:mm");
        return sdr.format(new Date(timeStamp)).replaceAll("#",
                getWeek(timeStamp));

    }

    private static String getWeek(long timeStamp) {
        int mydate = 0;
        String week = null;
        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date(timeStamp));
        mydate = cd.get(Calendar.DAY_OF_WEEK);
        // 获取指定日期转换成星期几
        if (mydate == 1) {
            week = "周日";
        } else if (mydate == 2) {
            week = "周一";
        } else if (mydate == 3) {
            week = "周二";
        } else if (mydate == 4) {
            week = "周三";
        } else if (mydate == 5) {
            week = "周四";
        } else if (mydate == 6) {
            week = "周五";
        } else if (mydate == 7) {
            week = "周六";
        }
        return week;
    }

    // 并用分割符把时间分成时间数组

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14-16-09-00"）
     *
     * @param time
     * @return
     */
    public String timesOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    public static String timesTwo(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * 并用分割符把时间分成时间数组
     *
     * @param time
     * @return
     */
    public static String[] timestamp(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        String[] fenge = times.split("[年月日时分秒]");
        return fenge;
    }

    /**
     * 根据传递的类型格式化时间
     *
     * @param str
     * @param type 例如：yy-MM-dd
     * @return
     */
    public static String getDateTimeByMillisecond(String str, String type) {

        Date date = new Date(Long.valueOf(str));

        SimpleDateFormat format = new SimpleDateFormat(type);

        String time = format.format(date);

        return time;
    }

    /**
     * 分割符把时间分成时间数组
     *
     * @param time
     * @return
     */
    public String[] division(String time) {

        String[] fenge = time.split("[年月日时分秒]");

        return fenge;

    }

    /**
     * 输入时间戳变星期
     *
     * @param time
     * @return
     */
    public static String changeweek(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        Date date = null;
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(times);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        return week;

    }

    /**
     * 获取日期和星期　例如：２０１４－１１－１３　１１:００　星期一
     *
     * @param time
     * @param type
     * @return
     */
    public static String getDateAndWeek(String time, String type) {
        return getDateTimeByMillisecond(time + "000", type) + "  "
                + changeweekOne(time);
    }

    /**
     * 输入时间戳变星期
     *
     * @param time
     * @return
     */
    public static String changeweekOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        Date date = null;
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(times);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        return week;

    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date());
    }

    /**
     * 输入日期如（2014年06月14日16时09分00秒）返回（星期数）
     *
     * @param time
     * @return
     */
    public String week(String time) {
        Date date = null;
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(time);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        return week;
    }

    /**
     * 输入日期如（2014-06-14-16-09-00）返回（星期数）
     *
     * @param time
     * @return
     */
    public String weekOne(String time) {
        Date date = null;
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(time);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        return week;
    }



    /**
     * <p>在工具类中经常使用到工具类的格式化描述，这个主要是一个日期的操作类，所以日志格式主要使用 SimpleDateFormat的定义格式.</p>
     * 格式的意义如下： 日期和时间模式 <br>
     * <p>日期和时间格式由日期和时间模式字符串指定。在日期和时间模式字符串中，未加引号的字母 'A' 到 'Z' 和 'a' 到 'z'
     * 被解释为模式字母，用来表示日期或时间字符串元素。文本可以使用单引号 (') 引起来，以免进行解释。"''"
     * 表示单引号。所有其他字符均不解释；只是在格式化时将它们简单复制到输出字符串，或者在分析时与输入字符串进行匹配。
     * </p>
     * 定义了以下模式字母（所有其他字符 'A' 到 'Z' 和 'a' 到 'z' 都被保留）： <br>
     * <table border="1" cellspacing="1" cellpadding="1" summary="Chart shows pattern letters, date/time component,
     * presentation, and examples.">
     * <tr>
     * <th align="left">字母</th>
     * <th align="left">日期或时间元素</th>
     * <th align="left">表示</th>
     * <th align="left">示例</th>
     * </tr>
     * <tr>
     * <td><code>G</code></td>
     * <td>Era 标志符</td>
     * <td>Text</td>
     * <td><code>AD</code></td>
     * </tr>
     * <tr>
     * <td><code>y</code> </td>
     * <td>年 </td>
     * <td>Year </td>
     * <td><code>1996</code>; <code>96</code> </td>
     * </tr>
     * <tr>
     * <td><code>M</code> </td>
     * <td>年中的月份 </td>
     * <td>Month </td>
     * <td><code>July</code>; <code>Jul</code>; <code>07</code> </td>
     * </tr>
     * <tr>
     * <td><code>w</code> </td>
     * <td>年中的周数 </td>
     * <td>Number </td>
     * <td><code>27</code> </td>
     * </tr>
     * <tr>
     * <td><code>W</code> </td>
     * <td>月份中的周数 </td>
     * <td>Number </td>
     * <td><code>2</code> </td>
     * </tr>
     * <tr>
     * <td><code>D</code> </td>
     * <td>年中的天数 </td>
     * <td>Number </td>
     * <td><code>189</code> </td>
     * </tr>
     * <tr>
     * <td><code>d</code> </td>
     * <td>月份中的天数 </td>
     * <td>Number </td>
     * <td><code>10</code> </td>
     * </tr>
     * <tr>
     * <td><code>F</code> </td>
     * <td>月份中的星期 </td>
     * <td>Number </td>
     * <td><code>2</code> </td>
     * </tr>
     * <tr>
     * <td><code>E</code> </td>
     * <td>星期中的天数 </td>
     * <td>Text </td>
     * <td><code>Tuesday</code>; <code>Tue</code> </td>
     * </tr>
     * <tr>
     * <td><code>a</code> </td>
     * <td>Am/pm 标记 </td>
     * <td>Text </td>
     * <td><code>PM</code> </td>
     * </tr>
     * <tr>
     * <td><code>H</code> </td>
     * <td>一天中的小时数（0-23） </td>
     * <td>Number </td>
     * <td><code>0</code> </td>
     * </tr>
     * <tr>
     * <td><code>k</code> </td>
     * <td>一天中的小时数（1-24） </td>
     * <td>Number </td>
     * <td><code>24</code> </td>
     * </tr>
     * <tr>
     * <td><code>K</code> </td>
     * <td>am/pm 中的小时数（0-11） </td>
     * <td>Number </td>
     * <td><code>0</code> </td>
     * </tr>
     * <tr>
     * <td><code>h</code> </td>
     * <td>am/pm 中的小时数（1-12） </td>
     * <td>Number </td>
     * <td><code>12</code> </td>
     * </tr>
     * <tr>
     * <td><code>m</code> </td>
     * <td>小时中的分钟数 </td>
     * <td>Number </td>
     * <td><code>30</code> </td>
     * </tr>
     * <tr>
     * <td><code>s</code> </td>
     * <td>分钟中的秒数 </td>
     * <td>Number </td>
     * <td><code>55</code> </td>
     * </tr>
     * <tr>
     * <td><code>S</code> </td>
     * <td>毫秒数 </td>
     * <td>Number </td>
     * <td><code>978</code> </td>
     * </tr>
     * <tr>
     * <td><code>z</code> </td>
     * <td>时区 </td>
     * <td>General time zone </td>
     * <td><code>Pacific Standard Time</code>; <code>PST</code>; <code>GMT-08:00</code> </td>
     * </tr>
     * <tr>
     * <td><code>Z</code> </td>
     * <td>时区 </td>
     * <td>RFC 822 time zone </td>
     * <td><code>-0800</code> </td>
     * </tr>
     * </table>
     * <pre>
     *                          HH:mm    15:44
     *                         h:mm a    3:44 下午
     *                        HH:mm z    15:44 CST
     *                        HH:mm Z    15:44 +0800
     *                     HH:mm zzzz    15:44 中国标准时间
     *                       HH:mm:ss    15:44:40
     *                     yyyy-MM-dd    2016-08-12
     *               yyyy-MM-dd HH:mm    2016-08-12 15:44
     *            yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
     *       yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
     *  EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
     *       yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     *   yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
     *                         K:mm a    3:44 下午
     *               EEE, MMM d, ''yy    星期五, 八月 12, '16
     *          hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
     *   yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
     *     EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
     *                  yyMMddHHmmssZ    160812154440+0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
     * </pre>
     */
    //Date格式
    public static final String DATE_FORMAT_LINK = "yyyyMMddHHmmssSSS";

    //Date格式 常用
    public static final String DATE_FORMAT_DETACH = "yyyy-MM-dd HH:mm:ss";

    //Date格式 带毫秒
    public static final String DATE_FORMAT_DETACH_SSS = "yyyy-MM-dd HH:mm:ss SSS";

    //时间格式 分钟：秒钟 一般用于视频时间显示
    public static final String DATE_FORMAT_MM_SS = "mm:ss";

    public static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat(DATE_FORMAT_DETACH, Locale.getDefault());


    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param milliseconds 毫秒时间戳
     * @return 时间字符串
     */
    public static String milliseconds2String(long milliseconds) {
        return milliseconds2String(milliseconds, DEFAULT_SDF);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为用户自定义</p>
     *
     * @param milliseconds 毫秒时间戳
     * @param format       时间格式
     * @return 时间字符串
     */
    public static String milliseconds2String(long milliseconds, SimpleDateFormat format) {
        return format.format(new Date(milliseconds));
    }

    /**
     * 将时间字符串转为时间戳
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    public static long string2Milliseconds(String time) {
        return string2Milliseconds(time, DEFAULT_SDF);
    }

    /**
     * 将时间字符串转为时间戳
     * <p>格式为用户自定义</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 毫秒时间戳
     */
    public static long string2Milliseconds(String time, SimpleDateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将时间字符串转为Date类型
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return Date类型
     */
    public static Date string2Date(String time) {
        return string2Date(time, DEFAULT_SDF);
    }

    /**
     * 将时间字符串转为Date类型
     * <p>格式为用户自定义</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return Date类型
     */
    public static Date string2Date(String time, SimpleDateFormat format) {
        return new Date(string2Milliseconds(time, format));
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time Date类型时间
     * @return 时间字符串
     */
    public static String date2String(Date time) {
        return date2String(time, DEFAULT_SDF);
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为用户自定义</p>
     *
     * @param time   Date类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String date2String(Date time, SimpleDateFormat format) {
        return format.format(time);
    }

    /**
     * 将Date类型转为时间戳
     *
     * @param time Date类型时间
     * @return 毫秒时间戳
     */
    public static long date2Milliseconds(Date time) {
        return time.getTime();
    }

    /**
     * 将时间戳转为Date类型
     *
     * @param milliseconds 毫秒时间戳
     * @return Date类型时间
     */
    public static Date milliseconds2Date(long milliseconds) {
        return new Date(milliseconds);
    }
    public enum TimeUnit {
        MSEC,
        SEC,
        MIN,
        HOUR,
        DAY
    }
    /**
     * 毫秒时间戳单位转换（单位：unit）
     *
     * @param milliseconds 毫秒时间戳
     * @param unit         <ul>
     *                     <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *                     <li>{@link TimeUnit#SEC }: 秒</li>
     *                     <li>{@link TimeUnit#MIN }: 分</li>
     *                     <li>{@link TimeUnit#HOUR}: 小时</li>
     *                     <li>{@link TimeUnit#DAY }: 天</li>
     *                     </ul>
     * @return unit时间戳
     */
    private static long milliseconds2Unit(long milliseconds, TimeUnit unit) {
        switch (unit) {
            case MSEC:
                return milliseconds / MSEC;
            case SEC:
                return milliseconds / SEC;
            case MIN:
                return milliseconds / MIN;
            case HOUR:
                return milliseconds / HOUR;
            case DAY:
                return milliseconds / DAY;
            default:
                break;
        }
        return -1;
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2格式都为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time0 时间字符串1
     * @param time1 时间字符串2
     * @param unit  <ul>
     *              <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *              <li>{@link TimeUnit#SEC }: 秒</li>
     *              <li>{@link TimeUnit#MIN }: 分</li>
     *              <li>{@link TimeUnit#HOUR}: 小时</li>
     *              <li>{@link TimeUnit#DAY }: 天</li>
     *              </ul>
     * @return unit时间戳
     */
    public static long getIntervalTime(String time0, String time1, TimeUnit unit) {
        return getIntervalTime(time0, time1, unit, DEFAULT_SDF);
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2格式都为format</p>
     *
     * @param time0  时间字符串1
     * @param time1  时间字符串2
     * @param unit   <ul>
     *               <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *               <li>{@link TimeUnit#SEC }: 秒</li>
     *               <li>{@link TimeUnit#MIN }: 分</li>
     *               <li>{@link TimeUnit#HOUR}: 小时</li>
     *               <li>{@link TimeUnit#DAY }: 天</li>
     *               </ul>
     * @param format 时间格式
     * @return unit时间戳
     */
    public static long getIntervalTime(String time0, String time1, TimeUnit unit, SimpleDateFormat format) {
        return Math.abs(milliseconds2Unit(string2Milliseconds(time0, format)
                - string2Milliseconds(time1, format), unit));
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2都为Date类型</p>
     *
     * @param time1 Date类型时间1
     * @param time2 Date类型时间2
     * @param unit  <ul>
     *              <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *              <li>{@link TimeUnit#SEC }: 秒</li>
     *              <li>{@link TimeUnit#MIN }: 分</li>
     *              <li>{@link TimeUnit#HOUR}: 小时</li>
     *              <li>{@link TimeUnit#DAY }: 天</li>
     *              </ul>
     * @return unit时间戳
     */
    public static long getIntervalTime(Date time1, Date time2, TimeUnit unit) {
        return Math.abs(milliseconds2Unit(date2Milliseconds(time2)
                - date2Milliseconds(time1), unit));
    }

    /**
     * 获取当前时间
     *
     * @return 毫秒时间戳
     */
    public static long getCurTimeMills() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @return 时间字符串
     */
    public static String getCurTimeString() {
        return date2String(new Date());
    }

    /**
     * 获取当前时间
     * <p>格式为用户自定义</p>
     *
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String getCurTimeString(SimpleDateFormat format) {
        return date2String(new Date(), format);
    }

    /**
     * 获取当前时间
     * <p>Date类型</p>
     *
     * @return Date类型时间
     */
    public static Date getCurTimeDate() {
        return new Date();
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @param unit <ul>
     *             <li>{@link TimeUnit#MSEC}:毫秒</li>
     *             <li>{@link TimeUnit#SEC }:秒</li>
     *             <li>{@link TimeUnit#MIN }:分</li>
     *             <li>{@link TimeUnit#HOUR}:小时</li>
     *             <li>{@link TimeUnit#DAY }:天</li>
     *             </ul>
     * @return unit时间戳
     */
    public static long getIntervalByNow(String time, TimeUnit unit) {
        return getIntervalByNow(time, unit, DEFAULT_SDF);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param unit   <ul>
     *               <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *               <li>{@link TimeUnit#SEC }: 秒</li>
     *               <li>{@link TimeUnit#MIN }: 分</li>
     *               <li>{@link TimeUnit#HOUR}: 小时</li>
     *               <li>{@link TimeUnit#DAY }: 天</li>
     *               </ul>
     * @param format 时间格式
     * @return unit时间戳
     */
    public static long getIntervalByNow(String time, TimeUnit unit, SimpleDateFormat format) {
        return getIntervalTime(getCurTimeString(), time, unit, format);
    }


    public static long getIntervalByNow(Date time, TimeUnit unit) {
        return getIntervalTime(getCurTimeDate(), time, unit);
    }

    /**
     * 判断闰年
     *
     * @param year 年份
     * @return {@code true}: 闰年<br>
     *          {@code false}: 平年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }



    //--------------------------------------------字符串转换成时间戳-----------------------------------

    /**
     * 将指定格式的日期转换成时间戳
     *
     * @param mDate
     * @return
     */
    public static String Date2Timestamp(Date mDate) {
        return String.valueOf(mDate.getTime()).substring(0, 10);
    }

    /**
     * 将日期字符串 按照 指定的格式 转换成 DATE
     * 转换失败时 return null;
     *
     * @param format
     * @param datess
     * @return
     */
    public static Date string2Date(String format, String datess) {
        SimpleDateFormat sdr = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdr.parse(datess);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将 yyyy年MM月dd日 转换成 时间戳
     *
     * @param format
     * @param datess
     * @return
     */
    public static String string2Timestamp(String format, String datess) {
        Date date = string2Date(format, datess);
        return Date2Timestamp(date);
    }




    public static String getEnglishMonth(long times){
        Date d = new Date(times);
        SimpleDateFormat sf = new SimpleDateFormat("MMM",Locale.ENGLISH);
        return sf.format(d);
    }



    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 判断当前日期是星期几
     *
     * @param strDate 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常<br>
     */
    public static int stringForWeek(String strDate) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(strDate));
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return 7;
        } else {
            return c.get(Calendar.DAY_OF_WEEK) - 1;
        }
    }

    /**
     * 判断当前日期是星期几
     *
     * @param strDate 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常<br>
     */
    public static int stringForWeek(String strDate, SimpleDateFormat simpleDateFormat) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(simpleDateFormat.parse(strDate));
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return 7;
        } else {
            return c.get(Calendar.DAY_OF_WEEK) - 1;
        }
    }

    /**
     * AM/PM
     */
    public static final String AM_PM = "a";
    /**
     * 一个月里第几天
     */
    public static final String DAY_IN_MONTH = "dd";
    /**
     * 一年里第几天
     */
    public static final String DAY_IN_YEAR = "DD";
    /**
     * 一周里第几天(Sunday,...)
     */
    public static final String DAY_OF_WEEK = "EEEE";
    /**
     * 以天为单位
     */
    public static final int DIFF_DAY = Calendar.DAY_OF_MONTH;
    /**
     * 以小时为单位
     */
    public static final int DIFF_HOUR = Calendar.HOUR_OF_DAY;
    /**
     * 以毫秒为单位
     */
    public static final int DIFF_MILLSECOND = Calendar.MILLISECOND;
    /**
     * 以分钟为单位
     */
    public static final int DIFF_MINUTE = Calendar.MINUTE;
    /**
     * 以月份为单位，按照每月30天计算
     */
    public static final int DIFF_MONTH = Calendar.MONTH;
    /**
     * 以秒为单位
     */
    public static final int DIFF_SECOND = Calendar.SECOND;
    /**
     * 以星期为单位，按照每星期7天计算
     */
    public static final int DIFF_WEEK = Calendar.WEEK_OF_MONTH;
    /**
     * 以年为单位，按照每年365天计算
     */
    public static final int DIFF_YEAR = Calendar.YEAR;
    /**
     * 半天内小时(0-11)
     */
    public static final String HOUR_IN_APM = "KK";
    /**
     * 一天内小时(0-23)
     */
    public static final String HOUR_IN_DAY = "HH";
    /**
     * 半天内小时(1-12)
     */
    public static final String HOUR_OF_APM = "hh";
    /**
     *  一天内小时(1-24)
     */
    public static final String HOUR_OF_DAY = "kk";

    /**
     * 年(四位)
     */
    public static final String LONG_YEAR = "yyyy";
    /**
     * 毫秒
     */
    public static final String MILL_SECOND = "SSS";
    /**
     * 分钟
     */
    public static final String MINUTE = "mm";
    /**
     * 月
     */
    public static final String MONTH = "MM";
    /**
     * 秒
     */
    public static final String SECOND = "ss";
    /**
     * 年(二位)
     */
    public static final String SHORT_YEAR = "yy";
    /**
     * 一个月里第几周
     */
    public static final String WEEK_IN_MONTH = "W";
    /**
     * 一年里第几周
     */
    public static final String WEEK_IN_YEAR = "ww";
    //设置时间时区
    static{
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
    /**
     * 检查目的时间是否已超过源时间值加上时间段长度
     * <p>
     * 用于判别当前是否已经超时
     *
     * @param destDate 目的时间，一般为当前时间
     * @param sourceDate 源时间，一般为事件产生时间
     * @param type 时间计算单位，为分钟、小时等
     * @param elapse 持续时间长度
     * @return 是否超时
     * @throws RuntimeException
     */
    public static boolean compareElapsedTime(
            Date destDate,
            Date sourceDate,
            int type,
            int elapse)
            throws RuntimeException {
        if (destDate == null || sourceDate == null)
            throw new RuntimeException("compared date invalid");

        return destDate.getTime() > getRelativeDate(sourceDate, type, elapse).getTime();
    }
    /**
     * 取当前时间字符串
     * <p>
     * 时间字符串格式为：年(4位)-月份(2位)-日期(2位) 小时(2位):分钟(2位):秒(2位)
     *
     * @return 时间字符串
     */
    public static String getCurrentDateString() {
        return getCurrentDateString("yyyy-MM-dd HH:mm:ss");
    }
    /**
     * 按格式取当前时间字符串
     *
     * @param formatString 格式字符串
     * @return
     */
    public static String getCurrentDateString(String formatString) {

        Date currentDate = new Date();
        return getDateString(currentDate, formatString);
    }

    /**
     * 取当天在一周的第几天
     * @return
     */
    public static int getCurrentDayOfWeek() {
        return getDayOfWeek(new Date());
    }
    /**
     *
     *  @Enclosing_Method: getCurrentDate
     *  @Written by: liuxmi
     *  @Creation Date: Jun 9, 2010 7:31:50 AM
     *  @version: v1.00
     *  @Description:获取当前时间
     *  @return
     *  @return Date
     *
     */
    public static Date  getCurrentDate() {
        return getDateFromString(getDateString(new Date(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
    }
    /**
     *
     *  @Enclosing_Method: getDate
     *  @Written by: liuxmi
     *  @Creation Date: Jun 9, 2010 7:32:11 AM
     *  @version: v1.00
     *  @Description:  日期格式化
     *  @param date
     *  @return
     *  @return Date
     *
     */
    public static Date getDate(Date date) {
        return getDateFromString(getDateString(date, "yyyy-MM-dd"), "yyyy-MM-dd");
    }
    /**
     * 根据时间字符串生成时间
     *
     * @param dateString 时间字符串格式
     * @return 时间
     * @throws RuntimeException
     */
    public static Date getDateFromString(String dateString)
            throws RuntimeException {
        return getDateFromString(dateString, "yyyy-MM-dd HH:mm:ss");
    }
    /**
     * 根据字符串生成时间
     *
     * @param dateString 时间字符串
     * @param pattern 时间字符串格式定义
     * @return 时间
     * @throws RuntimeException
     */
    public static Date getDateFromString(String dateString, String pattern)
            throws RuntimeException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (java.text.ParseException e) {
            throw new RuntimeException(
                    "parse date string '"
                            + dateString
                            + "' with pattern '"
                            + pattern
                            + "' failed: "
                            + e.getMessage());
        }
        return date;
    }
    /**
     * 取时间字符串
     *
     * @param date 时间
     * @return 时间字符串
     */
    public static String getDateString(Date date) {
        return getDateString(date, "yyyy-MM-dd HH:mm:ss");
    }
    /**
     * 取时间字符串
     *
     * @param date 时间
     * @param formatString 转换格式
     * @return 时间字符串
     */
    public static String getDateString(Date date, String formatString) {
        return getDateString(date, formatString, Locale.PRC);
    }

    /**
     * 取时间字符串
     *
     * @param date 时间
     * @param formatString 转换格式
     * @param locale 地区
     * @return 时间字符串
     */
    public static String getDateString(Date date, String formatString, Locale locale) {
        if (date == null)
            return null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatString);
        return dateFormat.format(date);
    }


    /**
     * 取日期在一周的第几天
     *
     * @param date 日期
     * @return
     */
    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    /**
     * 取日期在一月的第几天
     *
     * @param date 日期
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取一个月的最大天数
     *
     * @param date 日期
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取日期所在月份的最大天数
     *
     * @param date 日期
     * @return
     */
    public static int getMaximumDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.getMaximum(Calendar.DAY_OF_MONTH);
    }
    /**
     * 根据源时间和时长计算目的时间
     *
     * @param date 源时间
     * @param type 时间单位
     * @param relate 时长
     * @return 目的时间
     */
    public static Date getRelativeDate(Date date, int type, int relate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, relate);
        return calendar.getTime();
    }

    /**
     * 根据当前时间和时长计算目的时间
     *
     * @param type 时间单位
     * @param relate 时长
     * @return 目的时间
     */
    public static Date getRelativeDate(int type, int relate) {
        Date current = new Date();
        return getRelativeDate(current, type, relate);
    }
    /**
     * 根据当前时间和时长生成目的时间字符串
     *
     * @param type 时间单位
     * @param relate 时长
     * @param formatString 时间格式
     * @return 时间字符串
     */
    public static String getRelativeDateString(
            int type,
            int relate,
            String formatString) {
        return getDateString(getRelativeDate(type, relate), formatString);
    }

    public static Date getStartDate(Date date) {
        return getDateFromString(getDateString(date, "yyyyMMdd") + "00:00:00", "yyyyMMddHH:mm:ss");
    }

    public static Date getEndDate(Date date) {
        return getDateFromString(getDateString(date, "yyyyMMdd") + "23:59:59", "yyyyMMddHH:mm:ss");
    }

    /**
     * 取时间戳字符串
     *
     * @param date 时间
     * @return 时间戳字符串
     */
    public static String getTimestampString(Date date) {
        return getDateString(date, "yyyyMMddHHmmssSSS");
    }
    /**
     * 取当天日期值
     *
     * @return 日期的整数值
     */
    public static int getToday() {
        return Integer.parseInt(getCurrentDateString("dd"));
    }

    public static long getTimeDiff(Date fromDate, Date toDate, int type) {
        fromDate = (fromDate == null) ? new Date() : fromDate;
        toDate = (toDate == null) ? new Date() : toDate;
        long diff = toDate.getTime() - fromDate.getTime();

        switch(type) {
            case DIFF_MILLSECOND:
                break;

            case DIFF_SECOND:
                diff /= 1000;
                break;

            case DIFF_MINUTE:
                diff /= 1000 * 60;
                break;

            case DIFF_HOUR:
                diff /= 1000 * 60 * 60;
                break;

            case DIFF_DAY:
                diff /= 1000 * 60 * 60 * 24;
                break;

            case DIFF_MONTH:
                diff /= 1000 * 60 * 60 * 24 * 30;
                break;
            case DIFF_YEAR:
                diff /= 1000 * 60 * 60 * 24 * 365;
                break;

            default:
                diff = 0;
                break;
        }

        return diff;
    }

    /**
     * 比较时间戳是否相同
     *
     * @param arg0 时间
     * @param arg1 时间
     * @return 是否相同
     */
    public static boolean isTimestampEqual(Date arg0, Date arg1) {
        return getTimestampString(arg0).compareTo(getTimestampString(arg1)) == 0;
    }

    /**
     *
     *  @Enclosing_Method: nDaysAfterNowDate
     *  @Written by: liuxmi
     *  @Creation Date: May 25, 2010 6:11:01 AM
     *  @version: v1.00
     *  @Description:   当前日期加减n天后的日期
     *  @param n
     *  @return
     *  @return Date
     *
     */
    public static  Date   nDaysAfterNowDate(int   n)   {
        Calendar   rightNow   =   Calendar.getInstance();
        rightNow.add(Calendar.DAY_OF_MONTH,+n);
        return   rightNow.getTime();
    }

    /**
     *
     *  @Enclosing_Method: nDaysAfterOneDateString
     *  @Written by: liuxmi
     *  @Creation Date: May 25, 2010 6:12:37 AM
     *  @version: v1.00
     *  @Description:   给定一个日期型字符串，返回加减n天后的日期型字符串
     *  @param basicDate
     *  @param n
     *  @return
     *  @return String
     *
     */
    public  static String   nDaysAfterOneDateString(String   basicDate,int   n)   {
        SimpleDateFormat   df   =   new   SimpleDateFormat("yyyy-MM-dd");
        Date   tmpDate   =   null;
        try   {
            tmpDate   =   df.parse(basicDate);
        }
        catch(Exception   e){
            System.out.println("dateformat:"+e.getMessage());
        }
        long   nDay=(tmpDate.getTime()/(24*60*60*1000)+1+n)*(24*60*60*1000);
        tmpDate.setTime(nDay);

        return   df.format(tmpDate);
    }

    /**
     *
     *  @Enclosing_Method: nDaysAfterOneDate
     *  @Written by: liuxmi
     *  @Creation Date: May 25, 2010 6:13:45 AM
     *  @version: v1.00
     *  @Description:  给定一个日期，返回加减n天后的日期
     *  @param basicDate
     *  @param n
     *  @return
     *  @return Date
     *
     */
    public static   Date   nDaysAfterOneDate(Date   basicDate,int   n)   {
        long   nDay=(basicDate.getTime()/(24*60*60*1000)+n)*(24*60*60*1000);
        basicDate.setTime(nDay);
        return   basicDate;
    }

    /**
     *
     *  @Enclosing_Method: nDaysBetweenTwoDate
     *  @Written by: liuxmi
     *  @Creation Date: May 25, 2010 6:14:10 AM
     *  @version: v1.00
     *  @Description:  计算两个日期相隔的天数
     *  @param firstDate
     *  @param secondDate
     *  @return
     *  @return int
     *
     */
    public static  int   nDaysBetweenTwoDate(Date   firstDate,Date   secondDate)   {
        int   nDay=(int)((secondDate.getTime()-firstDate.getTime())/(24*60*60*1000));
        return   nDay;
    }
    /**
     *
     *  @Enclosing_Method: nYearsBetweenTwoDate
     *  @Written by: liuxmi
     *  @Creation Date: May 25, 2010 6:56:55 AM
     *  @version: v1.00
     *  @Description:  计算两个日期相隔的年数
     *  @param firstDate
     *  @param secondDate
     *  @return
     *  @return int
     *
     */
    public static  int   nYearsBetweenTwoDate(Date   firstDate,Date   secondDate)   {
        int   nYear=nDaysBetweenTwoDate(firstDate, secondDate)/365+1;
        return   nYear;
    }
    /**
     *
     *  @Enclosing_Method: nDaysBetweenTwoDate
     *  @Written by: liuxmi
     *  @Creation Date: May 25, 2010 6:32:15 AM
     *  @version: v1.00
     *  @Description: 计算两个日期相隔的天数
     *  @param firstString
     *  @param secondString
     *  @return
     *  @return int
     *
     */
    public  static  int   nDaysBetweenTwoDate(String   firstString,String   secondString)   {
        SimpleDateFormat   df   =   new   SimpleDateFormat("yyyy-MM-dd");
        Date   firstDate=null;
        Date   secondDate=null;
        try   {
            firstDate   =   df.parse(firstString);
            secondDate=df.parse(secondString);
        }
        catch(Exception   e)   {
            System.out.println("dateformat:"+e.getMessage());
        }
        int   nDay=(int)((secondDate.getTime()-firstDate.getTime())/(24*60*60*1000));
        return   nDay;
    }
    /**
     *
     *  @Enclosing_Method: getSundayOneDate
     *  @Written by: liuxmi
     *  @Creation Date: May 28, 2010 1:36:06 AM
     *  @version: v1.00
     *  @Description:  获取给定日期所在的周的第一天
     *  @param date
     *  @return void
     *
     */
    public static Date getFirstOfWeekOneDate(Date date){
        int day = CNDateUtils.getDayOfWeek(date);
        Date sunDay = CNDateUtils.getRelativeDate(date, CNDateUtils.DIFF_DAY,-(day-1) );
        return getDate(sunDay);
    }
    /**
     *
     *  @Enclosing_Method: getWeeksOfYear
     *  @Written by: liuxmi
     *  @Creation Date: May 28, 2010 4:02:48 AM
     *  @version: v1.00
     *  @Description:  返回给定日期在有一年中的第几周
     *  @param date
     *  @return
     *  @return int
     *
     */
    public static int getWeeksOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
}