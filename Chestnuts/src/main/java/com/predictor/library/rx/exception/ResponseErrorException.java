package com.predictor.library.rx.exception;

public class ResponseErrorException extends RuntimeException {
    public ResponseErrorException() {
    }

    public ResponseErrorException(String message) {
        super(message);
    }
}
