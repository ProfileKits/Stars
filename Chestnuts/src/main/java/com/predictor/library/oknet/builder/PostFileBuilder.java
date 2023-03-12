package com.predictor.library.oknet.builder;

import com.predictor.library.oknet.request.PostFileRequest;
import com.predictor.library.oknet.request.RequestCall;

import java.io.File;

import okhttp3.MediaType;

/**
 * 构建 Post File 请求 参数
 */
public class PostFileBuilder extends BaseRequestBuilder<PostFileBuilder> {

    private File file;
    private MediaType mediaType;

    public BaseRequestBuilder<PostFileBuilder> file(File file) {
        this.file = file;
        return this;
    }

    public BaseRequestBuilder<PostFileBuilder> mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostFileRequest(url, tag, params, headers, file, mediaType, id).build();
    }
}