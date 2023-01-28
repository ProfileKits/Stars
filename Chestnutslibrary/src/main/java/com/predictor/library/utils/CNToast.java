package com.predictor.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.Toast;


import com.predictor.library.R;

import java.lang.reflect.Field;

/**
 *
 */
public class CNToast {

    public static void showErrorNet(Context context) {
        show(context, R.string.net_exception);
    }

    public static void show(Context context, int res) {
        if (context == null) {
            return;
        }
        realShow(context, context.getString(res), Toast.LENGTH_SHORT);
    }

    public static void showErrorData(Context context) {
        if (context == null) {
            return;
        }
        realShow(context, context.getString(R.string.data_exception), Toast.LENGTH_SHORT);
    }


    public static void showNetError(Context context) {
        if (context == null) {
            return;
        }
        realShow(context, context.getString(R.string.net_exception), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String message) {
        if (context == null) {
            return;
        }
        realShow(context, message, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, String message) {
        if (context == null) {
            return;
        }
        realShow(context, message, Toast.LENGTH_LONG);
    }

    public static void showLong(Context context, int res) {
        if (context == null) {
            return;
        }
        realShow(context, context.getString(res), Toast.LENGTH_LONG);
    }

    public static void showUnkownError(Context ctx, Throwable t) {
        String message = "null";
        if (t != null) {
            message = t.getMessage();
        }
        show(ctx, ctx.getString(R.string.tip_unkown_error_place_holder, message));
    }

    private static void realShow(Context context, int res, int duration) {
        realShow(context, context.getString(res), duration);
    }

    private static void realShow(Context context, CharSequence text, int duration) {
        // 安卓7弹toast有可能因ui线程阻塞而崩溃，hook加个try,
        hook(Toast.makeText(context, text, duration)).show();
    }

    @SuppressWarnings("JavaReflectionMemberAccess")
    private static Toast hook(Toast toast) {
        Class<Toast> cToast = Toast.class;
        try {
            //TN是private的
            @SuppressLint("SoonBlockedPrivateApi") Field fTn = cToast.getDeclaredField("mTN");
            fTn.setAccessible(true);

            //获取tn对象
            Object oTn = fTn.get(toast);
            //获取TN的class，也可以直接通过Field.getType()获取。
            Class<?> cTn = oTn.getClass();
            Field fHandle = cTn.getDeclaredField("mHandler");

            //重新set->mHandler
            fHandle.setAccessible(true);
            fHandle.set(oTn, new HandlerProxy((Handler) fHandle.get(oTn)));
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
        return toast;
    }

    private static class HandlerProxy extends Handler {
        private Handler mHandler;
        HandlerProxy(Handler handler) {
            this.mHandler = handler;
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                mHandler.handleMessage(msg);
            } catch (WindowManager.BadTokenException ignored) {
                // 安卓8源码中handleShow方法就是直接加了try catch避免崩溃，所以这里也只try catch避免崩溃，
            }
        }
    }
}
