package com.predictor.library.net;

import retrofit2.Response;

public class ServiceHelper {
    public static  boolean checkObjectResponseCommon(Response response) {
        return response != null
                && response.body() != null;
    }
}