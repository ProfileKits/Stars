package com.predictor.library.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.predictor.library.jni.ChestnutData;

public class CNReceiverUtils {
    private static CNReceiverUtils receiverUtils;
    private static final Object LockThis = new Object();
    private ReceiverListener mListener;
    private RegisterListener mRListener;

    public synchronized static CNReceiverUtils getInstance(Context context, R listener) {
        synchronized (LockThis) {
            if (null == receiverUtils) {
                receiverUtils = new CNReceiverUtils(context, listener);
            }
        }
        return receiverUtils;
    }

    private CNReceiverUtils(Context context, R listener) {
        if (ChestnutData.getPermission()) {
            if (listener instanceof ReceiverListener) {
                mListener = (ReceiverListener) listener;
            } else if (listener instanceof RegisterListener) {
                mRListener = (RegisterListener) listener;
            }
            register(context);
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == CNBroadcastUtils.BROADCAST_ACTION) {
                if (intent.getBooleanExtra(CNBroadcastUtils.BROADCAST_CMD_UPDATE, false)) {
                    if (mListener != null) {
                        mListener.update();
                    }
                    if (mRListener != null) {
                        mRListener.update();
                    }
                } else if (intent.getBooleanExtra(CNBroadcastUtils.BROADCAST_CMD_DELETE, false)) {
                    if (mListener != null) {
                        mListener.delete();
                    }

                } else if (intent.getBooleanExtra(CNBroadcastUtils.BROADCAST_CMD_MODIFY, false)) {
                    if (mListener != null) {
                        mListener.modify();
                    }

                }
            }
        }
    };


    private void register(Context context) {
        IntentFilter intentFilter = new IntentFilter(CNBroadcastUtils.BROADCAST_ACTION);
        context.registerReceiver(receiver, intentFilter);
    }

    public interface ReceiverListener extends R {
        void update();

        void delete();

        void modify();
    }

    public interface RegisterListener extends R {
        void update();
    }


    private interface R {
    }
}
