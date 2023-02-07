package com.predictor.library.base;

import android.app.Application;

import com.predictor.library.jni.ChestnutData;
import com.predictor.library.net.HttpUrl;
import com.predictor.library.utils.CNLogUtil;

public abstract class CNBaseApp extends Application {

    public abstract boolean setDebug();

    public abstract String setBuglyKey();

    //测试地址和正式地址
    public abstract String[] setBaseUrl();

    @Override
    public void onCreate() {
        super.onCreate();
        String buglyKey = setBuglyKey();
        if(buglyKey!=null && !buglyKey.isEmpty()){
            initParams(setDebug(),buglyKey);
        }else{
            initParams(setDebug());
        }
        //32
        String[] baseUrl = setBaseUrl();
        if(baseUrl!=null && ChestnutData.getPermission()){
            HttpUrl.setBaseUrl(baseUrl.length>0?baseUrl[0]:"",baseUrl.length>1?baseUrl[1]:"");
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