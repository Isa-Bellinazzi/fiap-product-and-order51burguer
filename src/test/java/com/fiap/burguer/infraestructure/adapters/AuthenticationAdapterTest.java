package com.fiap.burguer.infraestructure.adapters;

import com.fiap.burguer.core.application.exception.RequestUnauthorized;
import com.fiap.burguer.core.application.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

class AuthenticationAdapterTest {

    private AuthenticationAdapter authenticationAdapter;
    private String validToken;
    private String invalidToken;

    @BeforeEach
    void setUp() {
        authenticationAdapter = new AuthenticationAdapter();
        validToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzQxOTM1MTgsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.2mOK0LBKuy2lAXFrEuoUQxTvHzXq8ypDS8vnW-b3sD8";
        invalidToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE2NTY2MzM2MDAsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.BmuDphXX7mlrL0kZpU6zQZGnng6iNnotWMsf5oADJEc";
    }

    @Test
    void testValidateAuthorizationHeader_withValidToken_shouldPass() {
        assertDoesNotThrow(() -> {
            authenticationAdapter.validateAuthorizationHeader(validToken);
        });
    }

    @Test
    void testValidateAuthorizationHeader_withNullHeader_shouldThrowException() {
        Exception exception = assertThrows(RequestUnauthorized.class, () -> {
            authenticationAdapter.validateAuthorizationHeader(null);
        });
        assertEquals("Token não fornecido ou inválido.", exception.getMessage());
    }

    @Test
    void testValidateAuthorizationHeader_withEmptyHeader_shouldThrowException() {
        Exception exception = assertThrows(RequestUnauthorized.class, () -> {
            authenticationAdapter.validateAuthorizationHeader(" ");
        });
        assertEquals("Token não fornecido ou inválido.", exception.getMessage());
    }

    @Test
    void testValidateAuthorizationHeader_withInvalidPrefix_shouldThrowException() {
        Exception exception = assertThrows(RequestUnauthorized.class, () -> {
            authenticationAdapter.validateAuthorizationHeader("Invalid token");
        });
        assertEquals("Tipo de token inválido. Esperado Bearer.", exception.getMessage());
    }

    @Test
    void testValidateIsAdminAccess_withAdminToken_shouldPass() {
        assertDoesNotThrow(() -> {
            authenticationAdapter.validateIsAdminAccess(validToken);
        });
    }

    @Test
    void testValidateIsAdminAccess_withNonAdminToken_shouldThrowException() {
        String nonAdminToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjpmYWxzZSwiZXhwIjoxNzMyNDY3NTgwLCJlbWFpbCI6Im1hcmlhTnVuZXNAZXhhbXBsZS5jb20ifQ.pKv6NO3CRAk4tpZRp5a8MvWfKlLi7NpJH0Hf2zYFO0g";

        Exception exception = assertThrows(RequestUnauthorized.class, () -> {
            authenticationAdapter.validateIsAdminAccess(nonAdminToken);
        });

        assertEquals("Usuário não tem permissão para acessar essa API.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTokenIsEmpty() {
        String authorizationHeader = "Bearer  ";
        RequestUnauthorized exception = assertThrows(
                RequestUnauthorized.class,
                () -> authenticationAdapter.validateAuthorizationHeader(authorizationHeader)
        );

        assertEquals("Token não fornecido ou inválido.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTokenIsExpired() {
        AuthenticationAdapter adapter = spy(new AuthenticationAdapter());
        String authorizationHeader = "Bearer expiredToken";
        doReturn(true).when(adapter).validateIsTokenExpired("expiredToken");
        RequestUnauthorized exception = assertThrows(
                RequestUnauthorized.class,
                () -> adapter.validateAuthorizationHeader(authorizationHeader)
        );

        assertEquals("Token expirou.", exception.getMessage());
    }



    @Test
    void testValidateIdUser_shouldReturnCorrectId() {
        Integer id = authenticationAdapter.validateIdUser(validToken);
        assertEquals(2, id);
    }
    @Test
    void testValidateIsTokenExpired_withNonExpiredToken_shouldReturnFalse() {
        Boolean isExpired = authenticationAdapter.validateIsTokenExpired(invalidToken);
        assertTrue(isExpired);
    }
}