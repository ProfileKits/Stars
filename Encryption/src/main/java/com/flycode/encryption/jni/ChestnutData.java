package com.flycode.encryption.jni;

import android.content.Context;

public class ChestnutData {
    static {
        System.loadLibrary("ChestnutData");
    }

    public static native boolean getToken(Context context, String key);

    public static native boolean getPermission();

    public static native String getKey(Context context);

    public static native String getStartCode();
}
