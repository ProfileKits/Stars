package com.predictor.library.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;


public class CNRectUtils {

    /**
     * 避免该view在ViewPager的第二屏幕宽度>screenWidth
     */
    public static final Rect clipScreenRect(Context context, Rect rect) {
        final int screenWidth = CNScreenUtil.getScreenWidth(context);

        if (rect.left > screenWidth) {
            rect.left -= screenWidth;
        }

        if (rect.right > screenWidth) {
            rect.right -= screenWidth;
        }

        final int screenHeight = CNScreenUtil.getScreenHeight(context);

        if (rect.top > screenWidth) {
            rect.top -= screenHeight;
        }

        if (rect.bottom > screenWidth) {
            rect.bottom -= screenHeight;
        }
        return rect;
    }


    // 按中心点等比增大
    public static Rect scaleRect(Context context, Rect src, float scale) {
        if (scale == 1.0f) {
            return src;
        }
        Rect newRect = src;

        int increaseX = (int) (newRect.width() * scale) / 2;
        int increaseY = (int) (newRect.height() * scale) / 2;

        newRect.left = newRect.left - increaseX;
        if (newRect.left < 0) {
            newRect.left = 0;
        }
        newRect.top = newRect.top - increaseY;
        if (newRect.top < 0) {
            newRect.top = 0;
        }
        newRect.right = newRect.right + increaseX;
        newRect.bottom = newRect.bottom + increaseY;

        final int screenWidth = CNScreenUtil.getScreenWidth(context);
        if (newRect.right > screenWidth) {
            newRect.right = screenWidth;
        }
        final int screenHeight = CNScreenUtil.getScreenHeight(context);

        return newRect;
    }

    // 按中心点等比增大
    public static Rect scaleRect(Context context, Rect src, int increaseX, int increaseY) {
        Rect newRect = src;

        newRect.left = newRect.left - increaseX;
        if (newRect.left < 0) {
            newRect.left = 0;
        }
        newRect.top = newRect.top - increaseY;
        if (newRect.top < 0) {
            newRect.top = 0;
        }
        newRect.right = newRect.right + increaseX;
        newRect.bottom = newRect.bottom + increaseY;

        final int screenWidth = CNScreenUtil.getScreenWidth(context);
        if (newRect.right > screenWidth) {
            newRect.right = screenWidth;
        }
        final int screenHeight = CNScreenUtil.getScreenHeight(context);

        return newRect;
    }
}
