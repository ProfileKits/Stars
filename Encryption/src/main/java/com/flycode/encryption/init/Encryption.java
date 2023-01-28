package com.flycode.encryption.init;

import android.content.Context;
import android.util.Log;

import com.flycode.encryption.jni.ChestnutData;


public class Encryption {
    private static Encryption Encryption;
    private static final Object LockThis = new Object();

    public synchronized static Encryption getInstance() {
        synchronized (LockThis) {
            if (null == Encryption) {
                Encryption = new Encryption();
            }
        }
        return Encryption;
    }

    public boolean init(Context context, String key) {
        boolean k = ChestnutData.getToken(context, key);
        if(!k){//初始化失败

        }else{
            String realKey = ChestnutData.getKey(context);
            boolean permission = ChestnutData.getPermission();
            Log.i("Encryption","realKey:"+realKey+ "--permission:"+permission);   
        }
        return k;
    }
}
