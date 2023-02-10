package com.predictor.galaxy.module;

import com.predictor.library.callback.CNCallbackback;

public class TestCallback implements CNCallbackback {
    @Override
    public String Go() {
        return "回调回调GoGoGo";
    }
}
