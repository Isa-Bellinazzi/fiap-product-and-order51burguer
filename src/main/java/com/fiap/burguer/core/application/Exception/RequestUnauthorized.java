package com.fiap.burguer.core.application.exception;

public class RequestUnauthorized extends RuntimeException {
    public RequestUnauthorized(String message) {
        super(message);
    }
}