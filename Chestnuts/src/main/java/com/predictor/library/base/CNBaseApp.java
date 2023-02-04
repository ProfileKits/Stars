package com.predictor.library.base;

import android.app.Application;

import com.predictor.library.jni.ChestnutData;
import com.predictor.library.utils.CNLogUtil;

public abstract class CNBaseApp extends Application {

    public abstract boolean setDebug();

    public abstract String setBuglyKey();

    @Override
    public void onCreate() {
        super.onCreate();
        String buglyKey = setBuglyKey();
        if(buglyKey!=null && !buglyKey.isEmpty()){
            initParams(setDebug(),buglyKey);
        }else{
            initParams(setDebug());
        }

    }

    private void initParams(boolean debugMode) {
        boolean key = CNBaseInvoke.getInstance().init(this, ChestnutData.getStartCode(),debugMode);
        CNLogUtil.i("key--is:"+key);
    }

    private void initParams(boolean debugMode,String buglyKey) {
        boolean key = CNBaseInvoke.getInstance().init(this, ChestnutData.getStartCode(),debugMode,buglyKey);
        CNLogUtil.i("key--is:"+key);
    }

}