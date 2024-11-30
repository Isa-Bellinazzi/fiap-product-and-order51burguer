package com.fiap.burguer.core.exception;

import com.fiap.burguer.core.application.exception.RequestException;
import com.fiap.burguer.core.application.exception.RequestUnauthorized;
import com.fiap.burguer.core.application.exception.ResourceNotAcceptableException;
import com.fiap.burguer.core.application.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionsTest {

    @Test
     void testThrowUnauthorizedException() {
        assertThrows(RequestUnauthorized.class, () -> {
            throw new RequestUnauthorized("Acesso não autorizado");
        });
    }

    @Test
     void testThrowResourceNotFoundException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            throw new ResourceNotFoundException("Recurso não encontrado");
        });
    }

    @Test
     void testThrowRequestException() {
        assertThrows(RequestException.class, () -> {
            throw new RequestException("Erro de requisição");
        });
    }

    @Test
     void testThrowGlobalException() {
        assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("Erro genérico");
        });
    }

    @Test
     void testThrowNotAcceptableException() {
        assertThrows(ResourceNotAcceptableException.class, () -> {
            throw new ResourceNotAcceptableException("Recurso não aceitável");
        });
    }
}
