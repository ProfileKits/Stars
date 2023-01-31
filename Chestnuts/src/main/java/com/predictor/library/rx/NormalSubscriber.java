package com.predictor.library.rx;


/**
 * <p>
 * Rxjava  默认onComplete()和onError()只会调用其中一个方法<br>
 * 改进后的MySubscriber在onComplete()和onError()都会调用onFinish();
 * </p>
 */

public abstract class NormalSubscriber<T> {
    public void onComplete() {
        onFinish();
    }

    /**
     * must be called super.onError when used.
     *
     * @param e
     */
    public void onError(Throwable e) {
//        Log.e("TAG", "onError() called!!!");

        e.printStackTrace();
        onFinish();
    }

    public abstract void onNext(T t);

    public void onFinish(){

    }
}
