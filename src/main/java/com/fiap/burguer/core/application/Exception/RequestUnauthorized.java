package com.fiap.burguer.core.application.Exception;

public class RequestUnauthorized extends RuntimeException {
    public RequestUnauthorized(String message) {
        super(message);
    }
}