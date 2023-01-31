package com.predictor.library.rx.exception;


public class ListEmptyException extends RuntimeException {
    public ListEmptyException() {
        super("暂无数据！");
    }

    public ListEmptyException(String message) {
        super(message);
    }

    public ListEmptyException(String message, Throwable cause) {
        super(message, cause);
    }
}
