package com.predictor.library.oknet.builder;

import com.predictor.library.oknet.request.PostStringRequest;
import com.predictor.library.oknet.request.RequestCall;

import okhttp3.MediaType;

/**
 * 构建 Post String 请求 参数
 */
public class PostStringBuilder extends BaseRequestBuilder<PostStringBuilder> {

    private String content;
    private MediaType mediaType;

    public PostStringBuilder content(String content) {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostStringRequest(url, tag, params, headers, content, mediaType, id).build();
    }
}