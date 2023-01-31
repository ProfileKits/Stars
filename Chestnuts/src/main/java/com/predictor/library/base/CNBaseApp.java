package com.predictor.library.base;

import android.app.Application;

import com.predictor.library.jni.ChestnutData;
import com.predictor.library.utils.CNLogUtil;

public abstract class CNBaseApp extends Application {

    public abstract boolean setDebug();

    @Override
    public void onCreate() {
        super.onCreate();
        initParams(setDebug());
    }

    private void initParams(boolean debugMode) {
        boolean key = CNBaseInvoke.getInstance().init(this, ChestnutData.getStartCode(),debugMode);
        CNLogUtil.i("key--is:"+key);
    }

}