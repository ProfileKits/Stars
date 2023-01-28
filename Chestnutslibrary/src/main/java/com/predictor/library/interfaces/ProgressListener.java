package com.predictor.library.interfaces;

public interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done, String name);
}
