package com.predictor.library.oknet.request;

import com.predictor.library.oknet.CNHttp;
import com.predictor.library.oknet.callback.BaseCallback;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 构建 Post File 请求
 */
public class PostFileRequest extends BaseRequest {

    private static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    private File file;
    private MediaType mediaType;

    public PostFileRequest(String url, Object tag, Map<String, Object> params, Map<String, Object> headers, File file, MediaType mediaType, int id) {
        super(url, tag, params, headers, id);
        this.file = file;
        this.mediaType = mediaType;
        if (this.file == null) {
            throw new IllegalArgumentException("the file can not be null !");
        }
        if (this.mediaType == null) {
            this.mediaType = MEDIA_TYPE_STREAM;
        }
    }

    @Override
    protected RequestBody buildRequestBody() {
        return RequestBody.create(mediaType, file);
    }

    @Override
    protected RequestBody wrapRequestBody(RequestBody requestBody, final BaseCallback baseCallback) {
        if (baseCallback == null) return requestBody;
        return new ProgressRequestBody(requestBody, new ProgressRequestBody.Listener() {
            @Override
            public void onRequestProgress(final long bytesWritten, final long contentLength) {
                CNHttp.getInstance().getDelivery().execute(new Runnable() {
                    @Override
                    public void run() {
                        baseCallback.inProgress(bytesWritten * 1.0f / contentLength, contentLength, id);
                    }
                });
            }
        });
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.post(requestBody).build();
    }
}