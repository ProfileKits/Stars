package com.predictor.library.utils;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;

import com.predictor.library.base.CNBaseActivity;



public class CNActionBarColorUtils {
    public static void titleTrans(CNBaseActivity activity, int color1, int color2) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color1);//系统标题栏背景设置透明色
            window.setNavigationBarColor(color2);//系统底部导航栏背景颜色
        }

        ActionBar actionBar = activity.getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }
    }

}
