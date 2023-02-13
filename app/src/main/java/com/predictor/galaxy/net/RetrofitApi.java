package com.predictor.galaxy.net;

import android.os.Handler;
import android.os.Looper;


import com.predictor.galaxy.bean.RankingBean;
import com.predictor.library.net.RetrofitUtil;
import com.predictor.library.rx.ApiResult;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.Executor;


import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * 直接转Object方法
 */
public class RetrofitApi implements RetrofitInterface {
    private static volatile RetrofitApi instance;
    private RetrofitInterface service;
    public Scheduler mainScheduler;
    public static RetrofitApi getInstance() {
        if (instance == null) {
            synchronized (RetrofitApi.class) {
                if (instance == null) {
                    try {
                        instance = new RetrofitApi();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    public RetrofitApi() throws NoSuchAlgorithmException {
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        service = retrofitUtil.getInstance().create(RetrofitInterface.class);
        mainScheduler = io.reactivex.schedulers.Schedulers.from(new Executor() {
            Handler handler = new Handler(Looper.getMainLooper());
            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        });
    }


    @Override
    public Single<RankingBean> getRankingToDay(Map<String, String> headers, String cityId) {
        return mapThread(service.getRankingToDay(headers,cityId));
    }


    public <T> Single<T> mapThread(Single<T> single) {
        return single.subscribeOn(Schedulers.newThread()).observeOn(mainScheduler);
    }
}
