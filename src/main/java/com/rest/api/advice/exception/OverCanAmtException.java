package com.rest.api.advice.exception;

public class OverCanAmtException extends RuntimeException{
    public OverCanAmtException(String msg, Throwable t) {
        super(msg, t);
    }

    public OverCanAmtException(String msg) {
        super(msg);
    }

    public OverCanAmtException() {
        super();
    }
}
