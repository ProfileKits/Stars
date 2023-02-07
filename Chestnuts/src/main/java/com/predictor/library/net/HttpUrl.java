package com.predictor.library.net;


import com.predictor.library.BuildConfig;
import com.predictor.library.jni.ChestnutData;

public class HttpUrl {
    private static String BASE_URL="";

    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static String RELEASES_URL = "https://releases";
    private static String TEST_URL = "https://test";

    public static void setBaseUrl(String testUrl,String releaseUrl){
        if (ChestnutData.getPermission()) {
            TEST_URL = testUrl;
            RELEASES_URL = releaseUrl;
        }
    }

    public static String getBaseUrl() {
        if (ChestnutData.getPermission()){
            if (DEBUG) {//测试地址
                BASE_URL = TEST_URL;
            } else {//正式地址
                BASE_URL = RELEASES_URL;
            }
        }
        return BASE_URL;
    }

    public static String downloadBaseUrl = "http://download.com/";//下载的基地址

    //登录态的测试数据
    public static String TestKey = "yU%2FPSwgsvQZUv1haCfJizKa0KDH5Ht56XAKQrr7HqHg%3D";
}
