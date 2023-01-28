package com.predictor.library.utils;

import android.app.Activity;
import android.view.WindowManager;

/**
 * 禁止或启用软键盘
 */
public class CNKeyboardUtil {
    private static Boolean hasKeyBoard = true;
    /*
     * 禁止软键盘
     * @param activity Activity
     */
    public static void disableKeyboard(Activity activity) {
        hasKeyBoard = false;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    /*
     * 启用软键盘
     * @param activity Activity
     */
    public static void enableKeyboard(Activity activity) {
        hasKeyBoard = true;
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    //当前状态
    public static boolean keyboardState(){
        return hasKeyBoard;
    }
}
