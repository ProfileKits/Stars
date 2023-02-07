package com.predictor.galaxy.net;


import com.predictor.galaxy.bean.RankingBean;
import com.predictor.library.rx.ApiResult;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;
//访问接口
public interface RetrofitServiceInter {
    @GET("/prod-api/app/user/getRankingToDay")
    Observable<ApiResult<RankingBean>> getRankingToDay(@HeaderMap Map<String, String> headers, @Query("cityId") String cityId);
}
