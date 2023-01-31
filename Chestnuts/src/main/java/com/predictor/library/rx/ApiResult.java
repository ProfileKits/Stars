package com.predictor.library.rx;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vijoz on 2020/5/5.
 */

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
