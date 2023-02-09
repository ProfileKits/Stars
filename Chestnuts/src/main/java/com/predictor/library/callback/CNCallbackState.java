package com.predictor.library.callback;

public interface CNCallbackState
{
    void success(Object obj);
    void failure(Object obj);
    void error(Object msg);
}
