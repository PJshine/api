package com.rest.api.advice.exception;

public class OverCanVatAmtException extends RuntimeException{
    public OverCanVatAmtException(String msg, Throwable t) {
        super(msg, t);
    }

    public OverCanVatAmtException(String msg) {
        super(msg);
    }

    public OverCanVatAmtException() {
        super();
    }
}
