package com.predictor.library.oknet;

import androidx.annotation.NonNull;

import com.predictor.library.jni.ChestnutData;
import com.predictor.library.oknet.builder.GetBuilder;
import com.predictor.library.oknet.builder.HeadBuilder;
import com.predictor.library.oknet.builder.OtherBuilder;
import com.predictor.library.oknet.builder.PostFileBuilder;
import com.predictor.library.oknet.builder.PostFormBuilder;
import com.predictor.library.oknet.builder.PostJsonBuilder;
import com.predictor.library.oknet.builder.PostJsonStrBuilder;
import com.predictor.library.oknet.builder.PostStringBuilder;
import com.predictor.library.oknet.callback.BaseCallback;
import com.predictor.library.oknet.executor.OkHttpExecutor;
import com.predictor.library.oknet.request.RequestCall;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class CNHttp {

    public static final long DEFAULT_MILLISECONDS = 10_000L;
    private volatile static CNHttp mInstance;
    private final OkHttpClient mOkHttpClient;
    private final OkHttpExecutor mOkHttpExecutor;

    private CNHttp(OkHttpClient okHttpClient) {
        if (okHttpClient == null && ChestnutData.getPermission()) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }
        mOkHttpExecutor = OkHttpExecutor.get();
    }

    /**
     * OkHttpUtils初始化方法，推荐在Application中全局初始化一次
     */
    public static CNHttp initClient(OkHttpClient okHttpClient) {
        if (mInstance == null && ChestnutData.getPermission()) {
            synchronized (CNHttp.class) {
                if (mInstance == null) {
                    mInstance = new CNHttp(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    /**
     * OkHttpUtils获取默认的OkHttpClient，可快速使用，但是不推荐
     */
    public static CNHttp getInstance() {
        return initClient(null);
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 获取线程池
     */
    public Executor getDelivery() {
        return mOkHttpExecutor.defaultCallbackExecutor();
    }

    // 对外暴漏的请求方法==========================================================start

    /**
     * GET 请求
     */
    public static GetBuilder get() {
        return new GetBuilder();
    }


    /**
     * POST 请求 和postJson()一样
     */
    public static PostJsonBuilder post() {
        return new PostJsonBuilder();
    }

    /**
     * POST Form 请求
     */
    public static PostFormBuilder postForm() {
        return new PostFormBuilder();
    }

    /**
     * POST Json 请求
     */
    public static PostJsonBuilder postJson() {
        return new PostJsonBuilder();
    }

    /**
     * POST Json Str 请求
     */
    public static PostJsonStrBuilder postJsonStr() {
        return new PostJsonStrBuilder();
    }

    /**
     * POST String 请求
     */
    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    /**
     * POST File 请求
     */
    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    /**
     * PUT 请求
     */
    public static OtherBuilder put() {
        return new OtherBuilder(METHOD.PUT);
    }

    /**
     * HEAD 请求
     */
    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    /**
     * DELETE 请求
     */
    public static OtherBuilder delete() {
        return new OtherBuilder(METHOD.DELETE);
    }

    /**
     * PATCH 请求
     */
    public static OtherBuilder patch() {
        return new OtherBuilder(METHOD.PATCH);
    }

    // 对外暴漏的请求方法===========================================================end

    public void execute(final RequestCall requestCall, BaseCallback baseCallback) {
        if (baseCallback == null) {
            baseCallback = BaseCallback.CALLBACK_BASE_DEFAULT;
        }
        final BaseCallback finalBaseCallback = baseCallback;
        final int id = requestCall.getOkHttpRequest().getId();
        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                sendFailResultCallback(call, e, finalBaseCallback, id);
            }

            @Override
            public void onResponse(@NonNull final Call call, @NonNull final Response response) {
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, new IOException("Canceled!"), finalBaseCallback, id);
                        return;
                    }
                    if (!finalBaseCallback.validateResponse(response, id)) {
                        sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalBaseCallback, id);
                        return;
                    }
                    Object o = finalBaseCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(o, finalBaseCallback, id);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalBaseCallback, id);
                } finally {
                    if (response.body() != null) {
                        response.body().close();
                    }
                }
            }
        });
    }

    public void sendFailResultCallback(final Call call, final Exception e, final BaseCallback baseCallback, final int id) {
        if (baseCallback == null) {
            return;
        }
        mOkHttpExecutor.execute(new Runnable() {
            @Override
            public void run() {
                baseCallback.onError(call, e, id);
                baseCallback.onAfter(id);
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final BaseCallback baseCallback, final int id) {
        if (baseCallback == null) return;
        mOkHttpExecutor.execute(new Runnable() {
            @Override
            public void run() {
                baseCallback.onResponse(object, id);
                baseCallback.onAfter(id);
            }
        });
    }

    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public static class METHOD {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }
}