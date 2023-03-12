package com.predictor.library.oknet.builder;

import com.predictor.library.oknet.CNttp;
import com.predictor.library.oknet.request.OtherRequest;
import com.predictor.library.oknet.request.RequestCall;

/**
 * 构建 HEAD 请求 参数
 */
public class HeadBuilder extends GetBuilder {

    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, CNttp.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}