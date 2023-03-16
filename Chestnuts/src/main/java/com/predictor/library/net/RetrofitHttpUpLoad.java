package com.predictor.library.net;

import com.predictor.library.jni.ChestnutData;
import com.predictor.library.listener.RetrofitCallBack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 上传文件工具类
 */
public class RetrofitHttpUpLoad {
    /**
     * 超时时间90s
     */
    private static final long DEFAULT_TIMEOUT = 90;
    private volatile static RetrofitHttpUpLoad mInstance;
    public Retrofit mRetrofit;
    private static Map<String, RequestBody> params;

    private RetrofitHttpUpLoad() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpUrl.getUploadUrl())
                .client(genericClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitHttpUpLoad getInstance() {
        if (mInstance == null && ChestnutData.getPermission()) {
            synchronized (RetrofitHttpUpLoad.class) {
                if (mInstance == null)
                    mInstance = new RetrofitHttpUpLoad();
                params = new HashMap<String, RequestBody>();
            }
        }
        return mInstance;
    }


    /**
     * 加入统一超时时间,http日志打印
     *
     * @return
     */
    private OkHttpClient genericClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        return httpClient;
    }


    /**
     * 将call加入队列并实现回调
     *
     * @param call             调入的call
     * @param retrofitCallBack 回调
     * @param method           调用方法标志。回调用
     * @param <T>              泛型參数
     */
    public <T> void addToEnqueue(Call<T> call, final RetrofitCallBack retrofitCallBack, final int method) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, retrofit2.Response<T> response) {
                if (null != response.body()) {
                    if (response.code() == 200) {
                        retrofitCallBack.onResponse(response, method);
                    } else {
                        retrofitCallBack.onFailure(response, method);
                    }
                } else {
                    retrofitCallBack.onFailure(response, method);
                }
            }


            @Override
            public void onFailure(Call<T> call, Throwable t) {;
                t.printStackTrace();
                retrofitCallBack.onFailure(null, method);
            }
        });
    }

    /**
     * 加入參数
     * 依据传进来的Object对象来推断是String还是File类型的參数
     */
    public RetrofitHttpUpLoad addParameter(String key, Object o) {
        if (o instanceof String) {
            RequestBody body = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), (String) o);
            params.put(key, body);
        } else if (o instanceof File) {
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data;charset=UTF-8"), (File) o);
            params.put(key + "\"; filename=\"" + ((File) o).getName() + "", body);
        }
        return this;
    }


    /**
     * 构建RequestBody
     */
    public Map<String, RequestBody> bulider() {
        return params;
    }

    public void clear() {
        params.clear();
    }
}