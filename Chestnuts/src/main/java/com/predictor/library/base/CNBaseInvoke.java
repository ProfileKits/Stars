package com.predictor.library.base;

import android.annotation.SuppressLint;
import android.content.Context;

import com.predictor.library.jni.ChestnutData;
import com.predictor.library.utils.CNBaseTools;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * 工具类初始化 主要识初始化CNBaseTools 和 CNImageTools
 */
public class CNBaseInvoke {
    private static CNBaseInvoke CNBaseInvoke;
    private static final Object LockThis = new Object();
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public synchronized static CNBaseInvoke getInstance() {
        synchronized (LockThis) {
            if (null == CNBaseInvoke) {
                CNBaseInvoke = new CNBaseInvoke();
            }
        }
        return CNBaseInvoke;
    }

    public boolean init(Context context, String key, boolean isDebug, String buglyKey) {
        this.context = context;
        CNBaseTools.init(context);
        boolean k = ChestnutData.getToken(context, key, isDebug);
        if (k) {
            CrashReport.initCrashReport(context, buglyKey, isDebug);
        }

//        String realKey = ChestnutData.getKey(context);
//        boolean permission = ChestnutData.getPermission();
//        CNLogUtil.i("realKey:"+realKey+ "--permission:"+permission);
        return k;
    }

    public boolean init(Context context, String key, boolean isDebug) {
        this.context = context;
        CNBaseTools.init(context);
        boolean k = ChestnutData.getToken(context, key, isDebug);
//        String realKey = ChestnutData.getKey(context);
//        boolean permission = ChestnutData.getPermission();
//        CNLogUtil.i("realKey:"+realKey+ "--permission:"+permission);
        return k;
    }


    public String getSign(Context context) {
        return ChestnutData.getKey(context);
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }
}
