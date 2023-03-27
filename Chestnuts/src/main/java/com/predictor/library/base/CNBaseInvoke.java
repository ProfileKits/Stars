package com.predictor.library.base;

import android.annotation.SuppressLint;
import android.content.Context;

import com.predictor.library.jni.ChestnutData;
import com.predictor.library.net.lib.CNetStateWatcher;
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
        //初始化网络状态变化监听器
        CNetStateWatcher.getInstance().getInstance(context);
        boolean k = ChestnutData.getToken(context, key, isDebug);
        if (k) {
            CrashReport.initCrashReport(context, buglyKey, isDebug);
        }
        return k;
    }

    public boolean init(Context context, String key, boolean isDebug) {
        this.context = context;
        CNBaseTools.init(context);
        boolean k = ChestnutData.getToken(context, key, isDebug);
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
