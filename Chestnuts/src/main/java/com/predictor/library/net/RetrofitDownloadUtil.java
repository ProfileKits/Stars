package com.predictor.library.net;


import com.predictor.library.interfaces.ProgressListener;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitDownloadUtil {
    /*超时设置*/
    private final int DEFAULT_TIMEOUT = 10;

    public RetrofitDownloadUtil() {}

    //下载文件
    public Retrofit downloadWork(ProgressListener progressListener, String name) {
        Retrofit dInstance;
        synchronized (RetrofitDownloadUtil.class) {
            OkHttpClient.Builder httpClient = null;
            try {
                httpClient = new OkHttpClient.Builder().sslSocketFactory(SSLContext.getDefault().getSocketFactory(),
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
                                }).connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).
                        readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).
                        writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .addNetworkInterceptor(chain -> {
                            Response originalResponse = chain.proceed(chain.request());
                            return originalResponse.newBuilder()
                                    .body(new ProgressResponseBody(originalResponse.body(), progressListener, name))
                                    .build();
                        });
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            dInstance = new Retrofit.Builder()
                    .baseUrl(HttpUrl.downloadBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .client(httpClient.build())
                    .build();
        }
        return dInstance;
    }

}