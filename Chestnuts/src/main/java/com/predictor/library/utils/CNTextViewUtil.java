package com.predictor.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.graphics.drawable.DrawableCompat;

import java.lang.reflect.Field;

//禁止输入框复制粘贴
public class CNTextViewUtil {
    /**
     * 改变TextView的字体颜色
     */
    public static void textColor(final TextView textView, boolean isHintColor, int color) {
        if (color != 0) {
            textView.setTextColor(color);
        } else {
            if (isHintColor) {
                textView.setTextColor(Color.parseColor("#8f8f8f"));
            } else {
                textView.setTextColor(Color.parseColor("#303030"));
            }
        }
    }

    /**
     * 给TextView控件设置左边Icon图标
     * @param context
     * @param drawableRes R.drawable.xxx
     * @param textView TextView object
     * @param drawableColor Color.parseColor("#000000")  等于零原色彩
     */
    public static void setTextLeftIcon(Context context,int drawableRes,final TextView textView,int drawableColor) {
        Drawable iconDrawable = context.getResources().getDrawable(drawableRes);
        if(drawableColor!=0) {
            Drawable drawable = CNEditTextUtil.tintDrawable(iconDrawable, ColorStateList.valueOf(drawableColor));
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(drawable, null, null, null);
        }else{
            iconDrawable.setBounds(0, 0, iconDrawable.getMinimumWidth(), iconDrawable.getMinimumHeight());
            textView.setCompoundDrawables(iconDrawable, null, null, null);
        }
    }

}