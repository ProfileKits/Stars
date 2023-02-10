package com.predictor.library.listener;

public interface RetrofitCallBack {
    <T> void onResponse(retrofit2.Response<T> response, int method);
    <T> void onFailure(retrofit2.Response<T> response, int method);
}
