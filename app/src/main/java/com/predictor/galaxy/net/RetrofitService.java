package com.predictor.galaxy.net;

import android.os.Handler;
import android.os.Looper;

import com.predictor.library.net.RetrofitUtil;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import io.reactivex.Scheduler;

import io.reactivex.schedulers.Schedulers;

//Retrofit 实例化工具类
public class RetrofitService {
    private static volatile RetrofitService instance;
    protected RetrofitServiceInter mWebApi;
    public Scheduler mainScheduler;
    public Map header = new HashMap();
    public RetrofitServiceInter getWebApi() {
        return mWebApi;
    }
    public static RetrofitService getInstance(){
        if (instance == null) {
            synchronized (RetrofitService.class) {
                if (instance == null) {
                    try {
                        instance = new RetrofitService();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    //获取token，添加到请求头中
    public Map getHeader() {
        return header;
    }

    //设置请求头的token 当用户登录成功后设置这个token
    public void setHeader(String token) {
        Map header = new HashMap();
        header.put("Authorization",token);
        this.header = header;
    }

    public RetrofitService()throws NoSuchAlgorithmException {
        RetrofitUtil retrofitUtil = new RetrofitUtil();
        mWebApi = retrofitUtil.getInstance().create(RetrofitServiceInter.class);
        mainScheduler = Schedulers.from(new Executor() {
            Handler handler = new Handler(Looper.getMainLooper());
            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        });
    }
}
