package com.rest.api.advice.exception;

public class CardPayException extends RuntimeException{
    public CardPayException(String msg, Throwable t) {
        super(msg, t);
    }

    public CardPayException(String msg) {
        super(msg);
    }

    public CardPayException() {
        super();
    }
}