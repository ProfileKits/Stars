package com.predictor.galaxy.net;

import android.content.Context;

import com.predictor.galaxy.bean.RankingBean;
import com.predictor.library.net.RetrofitUtil;
import com.predictor.library.rx.ApiResult;
import com.predictor.library.rx.NormalSubscriber;
import com.predictor.library.rx.RxTransformerHelper;
import com.predictor.library.utils.CNHttpUtil;
import com.predictor.library.utils.CNLogUtil;
import com.predictor.library.utils.CNToast;


import java.util.Map;

import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

public class RetrofitNetwork {

    private static RetrofitNetwork mRetrofitNetwork;
    private static final Object LockThis = new Object();

    public synchronized static RetrofitNetwork getInstance() {
        synchronized (LockThis) {
            if (null == mRetrofitNetwork) {
                mRetrofitNetwork = new RetrofitNetwork();
            }
        }
        return mRetrofitNetwork;
    }

    //测试方法
    public void testNetwork(Context context, NetResult callBack) {
        if (CNHttpUtil.isNetConnected(context)) {
            NormalSubscriber<ApiResult<RankingBean>> subscriber = new NormalSubscriber<ApiResult<RankingBean>>() {
                @Override
                public void onNext(ApiResult<RankingBean> result) {
                    if (result.code == 0) {//请求成功
                        RankingBean RankingBean = result.data;
                        callBack.success(result);
//                        CNToast.show(context, "请求数据成功:" + result.code);
                    } else {
                        callBack.error(result.msg);
                        //请求数据失败
//                        CNToast.show(context, "请求数据失败");
                    }
                }

                @Override
                public void onError(Throwable e) {
                    callBack.error("请求数据出错" + e.getMessage() + "-loc:" + e.getLocalizedMessage());
//                    CNToast.show(context, "请求数据出错");
                }
            };


            Disposable disposable = RetrofitService.getInstance().getWebApi().getRankingToDay(RetrofitService.getInstance().getHeader(), "3205")
                    .compose(RxTransformerHelper.applySchedulers())
                    .subscribe(data -> subscriber.onNext((ApiResult<RankingBean>) data), e -> subscriber.onError((Throwable) e));
            RetrofitUtil.addSubscription(disposable);
        } else {
            CNToast.show(context, "请检查网络连接");
        }
    }

    //提交同城快递订单
    public void submitTcExpressOrder(Context context, RequestBody body, NetResult callBack) {
        if (CNHttpUtil.isNetConnected(context)) {
            NormalSubscriber<ApiResult> subscriber = new NormalSubscriber<ApiResult>() {
                @Override
                public void onNext(ApiResult result) {
                    if (result.code == 200) {//请求成功
                        String object = (String) result.data;
                        CNLogUtil.i("请求数据成功:" + result.msg + "-data:" + object);
                        callBack.success(object);
//                        CNToast.show(context, "请求数据成功:" + result.code);
                    } else {
                        String message = "请求数据失败:" + "code:" + result.code + "--msg:" + result.msg;
                        callBack.error(message);
                        CNLogUtil.i(message);
                        //请求数据失败
//                        CNToast.show(context, "请求数据失败");
                    }
                }

                @Override
                public void onError(Throwable e) {
                    if(e.getMessage().equals("java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 34 path $.data")){
                        callBack.error("请求数据出错:转数据模型错误");
                        CNLogUtil.i("请求数据出错:转数据模型错误");
                    }else{
                        callBack.error("请求数据出错:" + e.getMessage());
                        CNLogUtil.i("请求数据出错:" + e.getMessage());
                    }

//                    CNToast.show(context, "请求数据出错");
                }
            };

            Disposable disposable = RetrofitService.getInstance().getWebApi().submitTcExpressOrder(RetrofitService.getInstance().getHeader(), body)
                    .compose(RxTransformerHelper.applySchedulers())
                    .subscribe(data -> subscriber.onNext((ApiResult) data), e -> subscriber.onError((Throwable) e));
            RetrofitUtil.addSubscription(disposable);
        } else {
            CNToast.show(context, "请检查网络连接");
        }
    }


    public interface NetResult {
        void success(Object result);

        void error(String msg);

    }

}
