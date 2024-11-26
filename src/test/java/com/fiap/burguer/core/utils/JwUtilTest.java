package com.fiap.burguer.core.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fiap.burguer.core.application.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil = new JwtUtil();
    String authorization = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzI0Njc1ODAsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.q_2DLSqswBncxJs3hbzFYVotfdiAl-shY6OI9Wq2Wug";

    @Test
    void testGetIdFromToken() {
        String token = authorization;

        Integer idFromToken = jwtUtil.getIdFromToken(token);

        assertEquals(2, idFromToken, "O ID do token não é o esperado.");
    }

    @Test
    void testGetIdFromTokenInvalid() {
        String invalidToken = "invalid.token.string";

        Integer idFromToken = jwtUtil.getIdFromToken(invalidToken);

        assertNull(idFromToken, "O ID do token deveria ser null para um token inválido.");
    }

    @Test
    void testGetCpfFromToken() {
        String token = JWT.create()
                .withClaim("cpf", "12345678909")
                .sign(Algorithm.HMAC256("secret"));
        String cpfFromToken = jwtUtil.getCpfFromToken(token);
        assertEquals("12345678909", cpfFromToken, "O CPF do token não é o esperado.");
    }

    @Test
    void testGetNameFromToken() {
        String token = JWT.create()
                .withClaim("name", "Maria Nunes")
                .sign(Algorithm.HMAC256("secret"));
        String nameFromToken = jwtUtil.getNameFromToken(token);
        assertEquals("Maria Nunes", nameFromToken, "O nome do token não é o esperado.");
    }

    @Test
    void testGetEmailFromToken() {
        String token = JWT.create()
                .withClaim("email", "maria.nunes@example.com")
                .sign(Algorithm.HMAC256("secret"));
        String emailFromToken = jwtUtil.getEmailFromToken(token);
        assertEquals("maria.nunes@example.com", emailFromToken, "O email do token não é o esperado.");
    }

    @Test
    void testIsTokenExpired() {
        String token = JWT.create()
                .withClaim("exp", System.currentTimeMillis() / 1000 + 3600)
                .sign(Algorithm.HMAC256("secret"));
        boolean isExpired = jwtUtil.isTokenExpired(token);
        assertFalse(isExpired, "O token não deveria estar expirado.");
    }

    @Test
    void testIsTokenExpiredExpired() {
        String token = JWT.create()
                .withClaim("exp", System.currentTimeMillis() / 1000 - 3600)
                .sign(Algorithm.HMAC256("secret"));
        boolean isExpired = jwtUtil.isTokenExpired(token);
        assertTrue(isExpired, "O token deveria estar expirado.");
    }

    @Test
    void testIsAdminFromToken() {
        String token = JWT.create()
                .withClaim("isAdmin", true)
                .sign(Algorithm.HMAC256("secret"));
        boolean isAdmin = jwtUtil.isAdminFromToken(token);
        assertTrue(isAdmin, "O token deveria indicar que o usuário é administrador.");
    }

    @Test
    void testIsAdminFromTokenFalse() {
        String token = JWT.create()
                .withClaim("isAdmin", false)
                .sign(Algorithm.HMAC256("secret"));
        boolean isAdmin = jwtUtil.isAdminFromToken(token);
        assertFalse(isAdmin, "O token deveria indicar que o usuário não é administrador.");
    }

    @Test
    void testIsTokenExpiredInvalidToken() {
        String invalidToken = "invalid.token.string";
        Boolean isExpired = jwtUtil.isTokenExpired(invalidToken);
        assertTrue(isExpired, "O token inválido deve ser considerado expirado.");
    }

    @Test
    void testIsAdminFromTokenInvalidToken() {
        String invalidToken = "invalid.token.string";
        boolean isAdmin = jwtUtil.isAdminFromToken(invalidToken);
        assertFalse(isAdmin, "O token inválido deve indicar que o usuário não é administrador.");
    }

}