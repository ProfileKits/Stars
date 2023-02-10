package com.predictor.library.net;


import com.predictor.library.BuildConfig;
import com.predictor.library.jni.ChestnutData;

public class HttpUrl {
    private static String BASE_URL = "";

    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static String RELEASES_URL = "https://releases";
    private static String TEST_URL = "https://test";
    private static String DOWNLOAD_URL = "";//下载地址
    private static String UPLOAD_URL = "";//上传地址

    public static void setBaseUrl(String testUrl, String releaseUrl) {
        if (ChestnutData.getPermission()) {
            TEST_URL = testUrl;
            RELEASES_URL = releaseUrl;
        }
    }

    public static void setDownloadUrl( String downloadUrl) {
        if (ChestnutData.getPermission()) {
            DOWNLOAD_URL = downloadUrl;
        }
    }

    public static void setUploadUrl( String uploadUrl) {
        if (ChestnutData.getPermission()) {
            UPLOAD_URL = uploadUrl;
        }
    }

    public static String getBaseUrl() {
        if (ChestnutData.getPermission()) {
            if (DEBUG) {//测试地址
                BASE_URL = TEST_URL;
            } else {//正式地址
                BASE_URL = RELEASES_URL;
            }
        }
        return BASE_URL;
    }

    public static String getDownloadUrl() {
        if (ChestnutData.getPermission()) {
            return DOWNLOAD_URL.isEmpty()?BASE_URL:DOWNLOAD_URL;
        } else {
            return null;
        }
    }

    public static String getUploadUrl() {
        if (ChestnutData.getPermission()) {
            return UPLOAD_URL.isEmpty()?BASE_URL:UPLOAD_URL;
        } else {
            return null;
        }
    }


    //登录态的测试数据
    public static String TestKey = "yU%2FPSwgsvQZUv1haCfJizKa0KDH5Ht56XAKQrr7HqHg%3D";
}
