package com.predictor.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装一些日志打印，统一方式打印一些麻烦的对象，
 * 方便崩溃上报后排查问题，
 */
public final class CNLog {
    private static final String TAG = "PRINTDATA";
    //这个值有的时候设置3是正确的行数，有时是7，要试一下哪个值，在Application中设置一下
    private static int mThread = 7;

    public static void setT(int thread) {
        CNLog.mThread = thread;
    }

    private CNLog() {
    }

    public static void PRINTDATA(Object obj) {
        PRINTDATA(TAG, obj);
    }

    public static void PRINTDATA(String tag, Object obj) {
        if (obj instanceof Intent) {
            PRINTDATA(tag, (Intent) obj);
        } else if (obj instanceof Bundle) {
            PRINTDATA(tag, (Bundle) obj);
        } else if (obj instanceof CharSequence) {
            PRINTDATA(tag, obj.toString());
        } else {
            PRINTDATA(tag, JSON.toJSONString(obj));
        }
    }

    private static void PRINTDATA(String tag, Intent intent) {
        StringBuilder sb = new StringBuilder();
        sb.append("intent = ");
        if (intent == null) {
            sb.append("null").append('\n');
        } else {
            sb.append(intent.toString()).append('\n');
            sb.append("action = ").append(intent.getAction()).append('\n');
            sb.append("data = ").append(intent.getDataString()).append('\n');
            sb.append("extra = ");
            Bundle bundle = intent.getExtras();
            if (bundle == null) {
                sb.append("null").append('\n');
            } else {
                Map<String, Object> map = new HashMap<>();
                for (String key : bundle.keySet()) {
                    Object value = bundle.get(key);
                    map.put(key, value);
                }
                sb.append(map.toString());
            }
        }
        realLog(tag, sb.toString());
    }

    private static void PRINTDATA(String tag, Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        sb.append("bundle = ");
        Map<String, Object> map = new HashMap<>();
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            if (value != null) {
                map.put(key, value);
            }
        }
        sb.append(map.toString());
        realLog(tag, sb.toString());
    }

    // 跟踪到调用该日志的方法
    private static StackTraceElement getCallerMethodName() {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        return stacks[mThread];
    }

    /**
     * 生成Tag
     *
     * @param stackTraceElement
     * @return
     */
    private static String generateTag(String tt, StackTraceElement stackTraceElement) {
        String tag = TAG + (tt.equals("PRINTDATA") ? "" : ("->" + tt)) + "【%s】%d行";
        String callerClazzName = stackTraceElement.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName
                .lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName,
                stackTraceElement.getLineNumber());
        return tag;
    }

    private static void PRINTDATA(String tag, String str) {
        realLog(tag, str);
    }

    @SuppressLint("LogTagMismatch")
    private static void realLog(String tag, String str) {
        StackTraceElement caller = getCallerMethodName();
        String t = generateTag(tag, caller);
        // TAG长度不能超过23，否则崩溃，
//        if (t.length() > 23) {
//            t = t.substring(0, 23);
//        }
        if (Log.isLoggable(t, Log.WARN)) {
            Log.w(t, str);
        }
    }


}
