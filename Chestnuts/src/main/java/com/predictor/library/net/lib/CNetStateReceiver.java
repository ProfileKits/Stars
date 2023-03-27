package com.predictor.library.net.lib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 网络状态广播接收者
 */
public class CNetStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        CNetStateWatcher.getInstance().post();
    }
}
