package com.predictor.library.utils;

import static com.predictor.library.utils.CNScreenUtil.dip2px;
import static com.predictor.library.utils.CNScreenUtil.px2dip;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.predictor.library.R;
import com.predictor.library.jni.ChestnutData;
import com.predictor.library.widgets.FontTextView;


public class CNToastCustom {
    private static int mWidth = 280;
    private static int mHeight = 120;
    private static int mLen = 10;//让文本显示在左侧的最大长度
    private static int mAlpha = 179;//0-255
    private static int mTextSize = 16;

    public static void showCustomToast(Context context,String message, int width, int height,int length,int alpha,int textSize,boolean isWhite) {
        if(!ChestnutData.getPermission()){
            return;
        }
        mWidth = width;
        mHeight = height;
        mLen = length;
        mAlpha = alpha;
        mTextSize = textSize;
        show(context, message, isWhite);
    }

    public static void showScanToast(Context context, String message) {
        if(!ChestnutData.getPermission()){
            return;
        }
        if (context == null)
            return;
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout toastView = (LinearLayout) inflater.inflate(R.layout.layout_toast, null);
        FontTextView messageView = (FontTextView) toastView.findViewById(R.id.message);
        messageView.setTextSize(mTextSize);
        messageView.setText(message);
        // 实例化一个Toast对象
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, dip2px(context, 200));
        toast.setView(toastView);
        toast.getView().getBackground().setAlpha(mAlpha);
        toast.show();
    }

    private static void show(Context context, String message, boolean isWhite) {
        if(!ChestnutData.getPermission()){
            return;
        }
        // 获取LayoutInflater对象，该对象可以将布局文件转换成与之一致的view对象
        if (context == null)
            return;
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout toastView;
        if (isWhite) {
            toastView = (LinearLayout) inflater.inflate(R.layout.toast_white, null);
        } else {
            toastView = (LinearLayout) inflater.inflate(R.layout.toast_balck, null);
        }
        TextView messageView = (TextView) toastView.findViewById(R.id.msg);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LayoutParams layoutParams = (LayoutParams) messageView.getLayoutParams();
        int len = mLen;
        if (message.length() > len) {
            layoutParams.width = dip2px(context,mWidth);
            layoutParams.height = dip2px(context,mHeight);
            layoutParams.gravity = Gravity.LEFT;
        } else {
            layoutParams.width = dip2px(context,mWidth);
            layoutParams.height = dip2px(context,mHeight);
            layoutParams.gravity = Gravity.CENTER;
        }
        toastView.setLayoutParams(params);
        toastView.setGravity(Gravity.CENTER);
        messageView.setLayoutParams(layoutParams);
        messageView.setTextSize(mTextSize);
        // 设置TextView的text内容
        messageView.setText(message);
        // 实例化一个Toast对象
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(toastView);
        if (!isWhite) {
            toast.getView().getBackground().setAlpha(mAlpha);
        }
        toast.show();
    }

    public static void showCalendarToast(Context context, String message) {
        if(!ChestnutData.getPermission()){
            return;
        }
        // 获取LayoutInflater对象，该对象可以将布局文件转换成与之一致的view对象
        if (context == null)
            return;
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout toastView = (LinearLayout) inflater.inflate(R.layout.toast_balck, null);
        TextView messageView = (TextView) toastView.findViewById(R.id.msg);
        if (toastView == null) return;
        // 设置TextView的text内容
        messageView.setTextSize(mTextSize);
        messageView.setText(message);
        // 实例化一个Toast对象
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.setView(toastView);
        toast.getView().getBackground().setAlpha(mAlpha);
        toast.show();
    }

    public static void showBlackToast(Context context, String message) {
        if(!ChestnutData.getPermission()){
            return;
        }
        show(context, message, false);
    }

    public static void showWhiteToast(Context context, String message) {
        if(!ChestnutData.getPermission()){
            return;
        }
        show(context, message, true);
    }
}
