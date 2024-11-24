package com.fiap.burguer.core.application.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RequestException extends RuntimeException {

    public RequestException(String message) {
        super(message);
    }
}
