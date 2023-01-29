package com.predictor.library.base;

import android.content.Context;

import com.predictor.library.jni.ChestnutData;
import com.predictor.library.utils.CNBaseTools;
import com.predictor.library.utils.CNLogUtil;

/**
 * 工具类初始化 主要识初始化CNBaseTools 和 CNImageTools
 */
public class CNBaseInvoke {
    private static CNBaseInvoke CNBaseInvoke;
    private static final Object LockThis = new Object();

    public synchronized static CNBaseInvoke getInstance() {
        synchronized (LockThis) {
            if (null == CNBaseInvoke) {
                CNBaseInvoke = new CNBaseInvoke();
            }
        }
        return CNBaseInvoke;
    }

    public boolean init(Context context, String key, boolean isDebug) {
        CNBaseTools.init(context);
        boolean k = ChestnutData.getToken(context, key, isDebug);
//        String realKey = ChestnutData.getKey(context);
//        boolean permission = ChestnutData.getPermission();
//        CNLogUtil.i("realKey:"+realKey+ "--permission:"+permission);
        return k;
    }
}
