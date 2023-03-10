package com.predictor.library.oknet.request;

import android.text.TextUtils;

import com.predictor.library.oknet.CNttp;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.http.HttpMethod;

/**
 * 构建 DELETE、PUT、PATCH 等其他方法
 */
public class OtherRequest extends BaseRequest {

    private static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");

    private RequestBody requestBody;
    private String method;
    private String content;

    public OtherRequest(RequestBody requestBody, String content, String method, String url, Object tag, Map<String, Object> params, Map<String, Object> headers, int id) {
        super(url, tag, params, headers, id);
        this.requestBody = requestBody;
        this.method = method;
        this.content = content;
    }

    @Override
    protected RequestBody buildRequestBody() {
        if (requestBody == null && TextUtils.isEmpty(content) && HttpMethod.requiresRequestBody(method)) {
            throw new IllegalArgumentException("requestBody and content can not be null in method:" + method);
        }
        if (requestBody == null && !TextUtils.isEmpty(content)) {
            requestBody = RequestBody.create(MEDIA_TYPE_PLAIN, content);
        }
        return requestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        switch (method) {
            case CNttp.METHOD.PUT:
                builder.put(requestBody);
                break;
            case CNttp.METHOD.DELETE:
                if (requestBody == null) {
                    builder.delete();
                } else {
                    builder.delete(requestBody);
                }
                break;
            case CNttp.METHOD.HEAD:
                builder.head();
                break;
            case CNttp.METHOD.PATCH:
                builder.patch(requestBody);
                break;
        }
        return builder.build();
    }
}