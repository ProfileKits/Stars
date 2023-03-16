package com.predictor.library.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.predictor.library.utils.CNBroadcastUtils;
import com.predictor.library.utils.CNReceiverFreedomUtils;

import java.util.List;

public class ReceiverBase {
    private RegisterListener mListener;
    private String action;
    private String cmd;



    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == CNBroadcastUtils.BROADCAST_ACTION) {
                if (intent.getBooleanExtra(CNBroadcastUtils.BROADCAST_CMD_UPDATE, false)) {
                    if (mListener != null) {
                        mListener.update();
                    }
                }

            }
        }
    };

    public interface RegisterListener{
        void update();
    }

}
