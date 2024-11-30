package com.fiap.burguer.infraestructure.adapters;
import com.fiap.burguer.core.application.exception.RequestUnauthorized;
import com.fiap.burguer.core.application.ports.AuthenticationPort;
import com.fiap.burguer.core.application.utils.JwtUtil;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationAdapter implements AuthenticationPort {
    private final JwtUtil jwtUtil;

    String bearerPrefix = "Bearer ";

    public AuthenticationAdapter() {
        this.jwtUtil = new JwtUtil();
    }

    @Override
    public void validateAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.trim().isEmpty()) {
            throw new RequestUnauthorized("Token não fornecido ou inválido.");
        }

        if (!authorizationHeader.regionMatches(true, 0, bearerPrefix, 0, bearerPrefix.length())) {
            throw new RequestUnauthorized("Tipo de token inválido. Esperado Bearer.");
        }

        String token = authorizationHeader.substring(bearerPrefix.length()).trim();

        if (token.isEmpty()) {
            throw new RequestUnauthorized("Token não fornecido ou inválido.");
        }

        if (validateIsTokenExpired(token)) {
            throw new RequestUnauthorized("Token expirou.");
        }
    }

    @Override
    public Boolean validateIsTokenExpired(String authorizationHeader) {
        return jwtUtil.isTokenExpired(authorizationHeader);
    }

    @Override
    public void validateIsAdminAccess(String authorizationHeader) {
        String token = authorizationHeader.substring(bearerPrefix.length()).trim();

        if (!jwtUtil.isAdminFromToken(token)) {
            throw new RequestUnauthorized("Usuário não tem permissão para acessar essa API.");
        }
    }

    @Override
    public Integer validateIdUser(String authorizationHeader) {
        String token = authorizationHeader.substring(bearerPrefix.length()).trim();
        return jwtUtil.getIdFromToken(token);
    }
}

