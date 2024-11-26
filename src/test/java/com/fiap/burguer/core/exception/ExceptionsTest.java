package com.fiap.burguer.core.exception;

import com.fiap.burguer.core.application.Exception.RequestException;
import com.fiap.burguer.core.application.Exception.RequestUnauthorized;
import com.fiap.burguer.core.application.Exception.ResourceNotAcceptableException;
import com.fiap.burguer.core.application.Exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionsTest {

    @Test
    public void testThrowUnauthorizedException() {
        assertThrows(RequestUnauthorized.class, () -> {
            throw new RequestUnauthorized("Acesso não autorizado");
        });
    }

    @Test
    public void testThrowResourceNotFoundException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            throw new ResourceNotFoundException("Recurso não encontrado");
        });
    }

    @Test
    public void testThrowRequestException() {
        assertThrows(RequestException.class, () -> {
            throw new RequestException("Erro de requisição");
        });
    }

    @Test
    public void testThrowGlobalException() {
        assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("Erro genérico");
        });
    }

    @Test
    public void testThrowNotAcceptableException() {
        assertThrows(ResourceNotAcceptableException.class, () -> {
            throw new ResourceNotAcceptableException("Recurso não aceitável");
        });
    }
}
