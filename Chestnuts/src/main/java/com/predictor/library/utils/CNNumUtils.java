package com.predictor.library.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;

import java.math.BigDecimal;

public class CNNumUtils {

    public static float round(float value, int scale) {
        return round(value, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static float round(float value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        float d = bd.floatValue();
        bd = null;
        return d;
    }

    /**
     * 保留一位小数
     *
     * @param count
     * @return
     */
    public static float caclutSaveOnePoint(double count) { // 保留小数点后一位小数
        try {
            BigDecimal b = new BigDecimal(count);
            double f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            return (float) f1;
        } catch (Exception e) {
            return 0;
        }


    }

    /**
     * 给一个数字设置不同的大小，从小数点开始分割
     *
     * @param context
     * @param num     一位小数数字 53.3
     * @param s1      小数前面文字的大小
     * @param s2      小数的大小
     * @return
     */
    public static SpannableStringBuilder setNumberSize(Context context, String num, float s1, float s2) {
        SpannableStringBuilder builder = new SpannableStringBuilder(num);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan((int) s1);
        AbsoluteSizeSpan sizeSpan1 = new AbsoluteSizeSpan((int) s2);
        String[] split = num.split("\\.");
        int end = split[0].length();
        builder.setSpan(sizeSpan, 0, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        builder.setSpan(sizeSpan1, end, num.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return builder;
    }


    /**
     * 保留两位小数
     *
     * @param count
     * @return
     */

    public static float calculateSaveNumberPoint(double count, int numberPoint) {// 保留小数点后两位小数
        BigDecimal b = new BigDecimal(count);
        double f1 = b.setScale(numberPoint, BigDecimal.ROUND_HALF_UP).doubleValue();
        return (float) f1;
    }


    /**
     * 四舍五入 float
     *
     * @param value
     * @return
     */
    public static int roundFloatToInt(float value) {
        return new BigDecimal(value).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }


    /**
     * 字符串是double类型String 例如 "12.0" 转成12 int类型
     * 注意：此方法会去掉小数点的转整型
     * @param value
     * @return
     */
    public static int StringToInt(String value) {
        return Double.valueOf(value).intValue();
    }

}
