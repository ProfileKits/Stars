package com.predictor.library.net;


import com.predictor.library.BuildConfig;

public class HttpUrl {
    private static final String BASE_URL;
    private static final boolean DEBUG = BuildConfig.DEBUG;
    public static final String RELEASES_URL = "https://run.com";
    public static final String TEST_URL = "http://test.com/";

    static {
        if (DEBUG) {//测试地址
            BASE_URL = TEST_URL;
        } else {//正式地址
            BASE_URL = RELEASES_URL;
        }
    }

    //首页列表地址
    public static final String HomePageList = BASE_URL +"index/index";

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String downloadBaseUrl = "http://download.com/";//下载的基地址

    //登录态的测试数据
    public static String TestKey = "yU%2FPSwgsvQZUv1haCfJizKa0KDH5Ht56XAKQrr7HqHg%3D";
}
