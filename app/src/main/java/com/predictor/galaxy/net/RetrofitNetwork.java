package com.predictor.galaxy.net;

import android.content.Context;

import com.predictor.galaxy.bean.RankingBean;
import com.predictor.library.listener.RetrofitCallBack;
import com.predictor.library.net.RetrofitHttpUpLoad;
import com.predictor.library.net.RetrofitUtil;
import com.predictor.library.rx.ApiResult;
import com.predictor.library.rx.NormalSubscriber;
import com.predictor.library.rx.RxTransformerHelper;
import com.predictor.library.utils.CNHttpUtil;
import com.predictor.library.utils.CNLog;
import com.predictor.library.utils.CNLogUtil;
import com.predictor.library.utils.CNToast;


import java.io.File;
import java.util.Map;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.SingleSubscriber;

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
            NormalSubscriber<ApiResult> subscriber = RetrofitSubscriber.get(callBack, 0);
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
            NormalSubscriber<ApiResult> subscriber = RetrofitSubscriber.get(callBack, 200);
            Disposable disposable = RetrofitService.getInstance().getWebApi().submitTcExpressOrder(RetrofitService.getInstance().getHeader(), body)
                    .compose(RxTransformerHelper.applySchedulers())
                    .subscribe(data -> subscriber.onNext((ApiResult) data), e -> subscriber.onError((Throwable) e));
            RetrofitUtil.addSubscription(disposable);
        } else {
            CNToast.show(context, "请检查网络连接");
        }
    }


    public void getAPIdata(Context context, NetResult callBack) {
        if (CNHttpUtil.isNetConnected(context)) {
            RetrofitApi.getInstance().getRankingToDay(RetrofitService.getInstance().getHeader(), "3205")
                    .subscribe(new SingleObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            CNLog.PRINTDATA(d);
                        }

                        @Override
                        public void onSuccess(Object o) {
                            CNLog.PRINTDATA(o);
                            callBack.success(o);
                        }

                        @Override
                        public void onError(Throwable e) {
                            CNLog.PRINTDATA(e);
                        }
                    });
        } else {
            CNToast.show(context, "请检查网络连接");
        }
    }


    /**
     * 上传文件/图片
     *
     * @param path    文件路径
     * @param context
     */
    private void uploadFile(String path, Context context) {
        RetrofitHttpUpLoad retrofitHttpUpLoad = RetrofitHttpUpLoad.getInstance();
        retrofitHttpUpLoad.clear();
        retrofitHttpUpLoad = retrofitHttpUpLoad.addParameter("file", new File(path));

        Map<String, RequestBody> params = retrofitHttpUpLoad
                .bulider();

        retrofitHttpUpLoad.addToEnqueue(RetrofitService.getInstance().getWebApi().upLoadData(params),
                new RetrofitCallBack() {
                    @Override
                    public <T> void onResponse(Response<T> response, int method) {
                        String msg = response.body().toString();
                        CNToast.show(context, "上传图片结果-成功:" + msg);
                        CNLogUtil.i("上传图片结果-成功:" + msg);
                    }

                    @Override
                    public <T> void onFailure(Response<T> response, int method) {
                        String msg = response.message();
                        CNToast.show(context, "上传图片结果-error:" + msg);
                        CNLogUtil.i("上传图片结果-error:" + msg);
                    }
                }, 0);
    }


    public interface NetResult {
        void success(Object result);

        void error(String msg);
    }

}
