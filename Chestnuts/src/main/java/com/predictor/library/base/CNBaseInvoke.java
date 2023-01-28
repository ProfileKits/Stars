package com.predictor.library.base;

import android.content.Context;
import com.predictor.library.utils.CNBaseTools;

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

    public void init(Context context) {
        CNBaseTools.init(context);
    }
}
