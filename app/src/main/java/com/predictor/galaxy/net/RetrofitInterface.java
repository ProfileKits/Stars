package com.predictor.galaxy.net;


import com.predictor.galaxy.bean.RankingBean;
import com.predictor.library.rx.ApiResult;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;


//访问接口
public interface RetrofitInterface {
    @GET("/prod-api/app/user/getRankingToDay")
    Single<RankingBean> getRankingToDay(@HeaderMap Map<String, String> headers, @Query("cityId") String cityId);
}

/**
 *   // 创建json对象
 *             JSONObject jsonObject = new JSONObject();
 *             // 1个数组参数
 *             JSONArray jsonArray = new JSONArray();
 *             for (String tag : tags) {
 *                 jsonArray.put(tag);
 *             }
 *             jsonObject.put("tags", jsonArray);
 *             // 3个字符串参数
 *             jsonObject.put("appId", appId);
 *             jsonObject.put("token", token);
 *             jsonObject.put("clientId", clientId);
 *             String data = jsonObject.toString();
 */