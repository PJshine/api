package com.rest.api.advice.exception;

public class AlreadyCancelException extends RuntimeException{
    public AlreadyCancelException(String msg, Throwable t) {
        super(msg, t);
    }

    public AlreadyCancelException(String msg) {
        super(msg);
    }

    public AlreadyCancelException() {
        super();
    }
}
