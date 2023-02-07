package com.predictor.library.rx;

import com.google.gson.annotations.SerializedName;

public class ApiResult<T> {
    @SerializedName("code")
    public int code;
    @SerializedName("msg")
    public String msg;
    @SerializedName("timestam")
    public int timestam;
    @SerializedName("data")
    public T data;
}
