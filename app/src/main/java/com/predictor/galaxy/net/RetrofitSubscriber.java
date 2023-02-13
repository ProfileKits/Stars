package com.predictor.galaxy.net;

import com.predictor.library.rx.ApiResult;
import com.predictor.library.rx.NormalSubscriber;
import com.predictor.library.utils.CNLogUtil;

public class RetrofitSubscriber {
    public static NormalSubscriber get(RetrofitNetwork.NetResult callBack,int successCode){
        NormalSubscriber<ApiResult> subscriber = new NormalSubscriber<ApiResult>() {
            @Override
            public void onNext(ApiResult result) {
                if (result.code == successCode) {//请求成功
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

            }
        };
        return subscriber;
    }
}
