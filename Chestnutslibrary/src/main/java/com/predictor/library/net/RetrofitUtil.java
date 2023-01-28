package com.predictor.library.net;

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private static volatile Retrofit sInstance;
    /*超时设置*/
    private static final int DEFAULT_TIMEOUT = 10;
    private static OkHttpClient.Builder okHttpClient;

    private static OkHttpClient.Builder getOkHttp() throws NoSuchAlgorithmException {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder();
            okHttpClient.sslSocketFactory(SSLContext.getDefault().getSocketFactory(),
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    });
            okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).
                    readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).
                    writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }

        return okHttpClient;
    }

    public static Retrofit getInstance() throws NoSuchAlgorithmException {
        if (sInstance == null) {
            synchronized (RetrofitUtil.class) {
                OkHttpClient.Builder httpClient = getOkHttp();
                sInstance = new Retrofit.Builder()
                        .baseUrl(HttpUrl.getBaseUrl())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .client(httpClient.build())
                        .build();
            }
        }
        return sInstance;
    }
}
