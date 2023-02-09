package com.predictor.library.jni;

import android.content.Context;

public class ChestnutData {
    static {
        System.loadLibrary("ChestnutData");
    }

    public static native boolean getToken(Context context, String key,boolean isDebug);

    public static native boolean getPermission();

    public static native String getKey(Context context);

    public static native String getStartCode();
}
