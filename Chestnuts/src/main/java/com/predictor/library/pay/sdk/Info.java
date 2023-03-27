package com.predictor.library.pay.sdk;

import com.predictor.library.pay.sdk.utils.AuthResult;

public class Info {
    private String name;
    private AuthResult authResult;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthResult getAuthResult() {
        return authResult;
    }

    public void setAuthResult(AuthResult authResult) {
        this.authResult = authResult;
    }

    public Info(String name, AuthResult authResult) {
        this.name = name;
        this.authResult = authResult;
    }

    public Info() {
    }
}
