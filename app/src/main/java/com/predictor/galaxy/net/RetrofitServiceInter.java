package com.predictor.galaxy.net;


import com.predictor.galaxy.bean.RankingBean;
import com.predictor.library.rx.ApiResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

//访问接口
public interface RetrofitServiceInter {
    @GET("/prod-api/app/user/getRankingToDay")
    Observable<ApiResult<RankingBean>> getRankingToDay(@HeaderMap Map<String, String> headers, @Query("cityId") String cityId);

    @POST("/prod-api/order/submitTcExpressOrder")
    Observable<ApiResult> submitTcExpressOrder(@HeaderMap Map<String, String> headers, @Body RequestBody body);

    @Multipart
    @POST("file/upLoad.do")//上传文件/图片接口
    Call<ApiResult> upLoadData(@PartMap Map<String, RequestBody> params);
}
