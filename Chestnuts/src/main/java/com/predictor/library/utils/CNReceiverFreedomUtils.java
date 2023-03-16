package com.predictor.library.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.predictor.library.base.ReceiverBase;
import com.predictor.library.jni.ChestnutData;

import java.util.ArrayList;
import java.util.List;

public class CNReceiverFreedomUtils {
    private static CNReceiverFreedomUtils receiverUtils;
    private static final Object LockThis = new Object();

    public synchronized static CNReceiverFreedomUtils getInstance(Context context,String action,String cmd, ReceiverBase.RegisterListener listener) {
        synchronized (LockThis) {
            if (null == receiverUtils) {
                receiverUtils = new CNReceiverFreedomUtils(context, listener);
            }
        }
        return receiverUtils;
    }

    private CNReceiverFreedomUtils(Context context, ReceiverBase.RegisterListener listener) {
        if (ChestnutData.getPermission()) {
//             if (listener instanceof RegisterListener) {
//                mListener = (RegisterListener) listener;
//            }
//            register(context);
        }
    }


//    private void register(Context context) {
//        IntentFilter intentFilter = new IntentFilter(CNBroadcastUtils.BROADCAST_ACTION);
//        context.registerReceiver(receiver, intentFilter);
//    }



//    public void unRegister(Context context) {
//        if (receiver != null) {
//            context.unregisterReceiver(receiver);
//        }
//    }



}
