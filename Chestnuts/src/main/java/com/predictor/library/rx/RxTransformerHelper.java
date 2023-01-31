package com.predictor.library.rx;



import com.predictor.library.rx.exception.ResponseErrorException;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxTransformerHelper {
    //转换线程
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //ApiResult<T> 可换成其他网络请求标准Json最外层格式模型
    public static <T> ObservableTransformer<ApiResult<T>, T> checkResponse() {
        return observable -> {
            return observable.map(data -> {
                if (data.code == 1000)
                    return data.data;
                else {
                    //通用错误
                    throw new ResponseErrorException(data.msg);
                }
            });
        };
    }
}
