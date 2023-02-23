package com.predictor.library.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.predictor.library.bean.BroadcastBean;
import com.predictor.library.jni.ChestnutData;

public class CNReceiverUtils {
    private static CNReceiverUtils receiverUtils;
    private static final Object LockThis = new Object();
    private ReceiverListener mListener;
    private RegisterListener mRListener;
    private RegisterStrListener mRSListener;
    private RegisterObjectListener mObjectListener;

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
            } else if (listener instanceof RegisterStrListener) {
                mRSListener = (RegisterStrListener) listener;
            }else if (listener instanceof RegisterObjectListener) {
                mObjectListener = (RegisterObjectListener) listener;
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
                }
                if (intent.getBooleanExtra(CNBroadcastUtils.BROADCAST_CMD_DELETE, false)) {
                    if (mListener != null) {
                        mListener.delete();
                    }

                }
                if (intent.getBooleanExtra(CNBroadcastUtils.BROADCAST_CMD_MODIFY, false)) {
                    if (mListener != null) {
                        mListener.modify();
                    }

                }
                if (intent.getStringExtra(CNBroadcastUtils.BROADCAST_CMD_STRING)!=null && !intent.getStringExtra(CNBroadcastUtils.BROADCAST_CMD_STRING).isEmpty()) {
                    if (mRSListener != null) {
                        String str = intent.getStringExtra(CNBroadcastUtils.BROADCAST_CMD_STRING);
                        mRSListener.receiver(str);
                    }

                }
                if (intent.getSerializableExtra(CNBroadcastUtils.BROADCAST_CMD_OBJECT)!=null){
                    if (mObjectListener != null) {
                        BroadcastBean obj = (BroadcastBean) intent.getSerializableExtra(CNBroadcastUtils.BROADCAST_CMD_OBJECT);
                        mObjectListener.receiver(obj);
                    }
                }
            }
        }
    };


    private void register(Context context) {
        IntentFilter intentFilter = new IntentFilter(CNBroadcastUtils.BROADCAST_ACTION);
        context.registerReceiver(receiver, intentFilter);
    }



    public void unRegister(Context context) {
        if (receiver != null) {
            context.unregisterReceiver(receiver);
        }
    }

    public interface ReceiverListener extends R {
        void update();

        void delete();

        void modify();
    }

    public interface RegisterListener extends R {
        void update();
    }

    public interface RegisterStrListener extends R {
        void receiver(String data);
    }

    public interface RegisterObjectListener extends R {
        void receiver(BroadcastBean bean);
    }

    private interface R {
    }
}
