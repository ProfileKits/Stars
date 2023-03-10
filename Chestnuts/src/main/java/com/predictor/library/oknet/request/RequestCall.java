package com.predictor.library.oknet.request;

import com.predictor.library.oknet.CNttp;
import com.predictor.library.oknet.callback.BaseCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestCall {

    private final BaseRequest baseRequest;
    private Request request;
    private Call call;

    private long readTimeOut;
    private long writeTimeOut;
    private long connTimeOut;

    public RequestCall(BaseRequest request) {
        this.baseRequest = request;
    }

    public RequestCall readTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public RequestCall writeTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    public RequestCall connTimeOut(long connTimeOut) {
        this.connTimeOut = connTimeOut;
        return this;
    }

    public Call buildCall(BaseCallback baseCallback) {
        request = generateRequest(baseCallback);
        if (readTimeOut > 0 || writeTimeOut > 0 || connTimeOut > 0) {
            readTimeOut = readTimeOut > 0 ? readTimeOut : CNttp.DEFAULT_MILLISECONDS;
            writeTimeOut = writeTimeOut > 0 ? writeTimeOut : CNttp.DEFAULT_MILLISECONDS;
            connTimeOut = connTimeOut > 0 ? connTimeOut : CNttp.DEFAULT_MILLISECONDS;
            OkHttpClient client = CNttp.getInstance().getOkHttpClient().newBuilder()
                    .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                    .writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS)
                    .connectTimeout(connTimeOut, TimeUnit.MILLISECONDS)
                    .build();
            call = client.newCall(request);
        } else {
            call = CNttp.getInstance().getOkHttpClient().newCall(request);
        }
        return call;
    }

    private Request generateRequest(BaseCallback baseCallback) {
        return baseRequest.generateRequest(baseCallback);
    }

    public void execute(BaseCallback baseCallback) {
        buildCall(baseCallback);
        if (baseCallback != null) {
            baseCallback.onBefore(request, getOkHttpRequest().getId());
        }
        CNttp.getInstance().execute(this, baseCallback);
    }

    public Call getCall() {
        return call;
    }

    public Request getRequest() {
        return request;
    }

    public BaseRequest getOkHttpRequest() {
        return baseRequest;
    }

    public Response execute() throws IOException {
        buildCall(null);
        return call.execute();
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }
}