package com.predictor.galaxy.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.predictor.galaxy.BuildConfig;
import com.predictor.library.base.CNBaseInvoke;
import com.predictor.library.jni.ChestnutData;
import com.predictor.library.utils.CNLogUtil;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class MyApplication extends Application {
    private static MyApplication myApplication;

    private static final Object LockThis = new Object();

    public synchronized static MyApplication getInstance() {
        synchronized (LockThis) {
            if (null == myApplication) {
                myApplication = new MyApplication();
            }
        }
        return myApplication;
    }

    public static final boolean DEBUG_MODE = BuildConfig.DEBUG;//是否为Debug模式

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }

    private void initData() {
       boolean key = CNBaseInvoke.getInstance().init(this, ChestnutData.getStartCode(),DEBUG_MODE);
        CNLogUtil.i("key:"+key);
//        String realKey = getSha1Value(this);
//        CNLogUtil.i("key:"+realKey);
    }

    private byte[] getSign(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> apps = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
        Iterator<PackageInfo> iter = apps.iterator();
        while (iter.hasNext()) {
            PackageInfo info = iter.next();
            String packageName = info.packageName;
            if (packageName.equals(context.getPackageName())) {
                return info.signatures[0].toByteArray();
            }
        }
        return null;
    }

    public String getSha1Value(Context context) {
        try {
            byte[] cert = getSign(context);
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
            }
            String result = hexString.toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
