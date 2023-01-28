package com.predictor.library.utils;

import android.os.Handler;
import android.view.View;

/**
 * 设定view在一定时间内无效，防止快速点击重复执行
 */
public class CNFastClickCheckUtil {
    /**
     * 相同视图点击必须间隔0.6s才能有效
     *
     * @param view 目标视图
     */
    public static void check(View view) {
        check(view, 600);
    }

    /**
     * 设置间隔点击规则，配置间隔点击时长
     *
     * @param view  目标视图
     * @param mills 点击间隔时间（毫秒）
     */
    public static void check(final View view, int mills) {
        view.setClickable(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setClickable(true);
            }
        }, mills);
    }
}