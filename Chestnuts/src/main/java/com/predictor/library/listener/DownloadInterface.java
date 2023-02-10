package com.predictor.library.listener;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface DownloadInterface {
    @GET
    @Streaming
    Call<ResponseBody> downLoad(@Url String fileUrl);
}