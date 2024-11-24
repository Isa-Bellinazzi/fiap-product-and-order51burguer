package com.fiap.burguer.core.application.utils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {

    public DecodedJWT decodeToken(String token) {
        return JWT.decode(token);
    }

    public Integer getIdFromToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaim("id").asInt();
        } catch (Exception e) {
            return null;
        }
    }

    public String getCpfFromToken(String token) {
        DecodedJWT decodedJWT = decodeToken(token);
        return decodedJWT.getClaim("cpf").asString();
    }

    public String getNameFromToken(String token) {
        DecodedJWT decodedJWT = decodeToken(token);
        return decodedJWT.getClaim("name").asString();
    }

    public String getEmailFromToken(String token) {
        DecodedJWT decodedJWT = decodeToken(token);
        return decodedJWT.getClaim("email").asString();
    }

    public Boolean isTokenExpired(String token) {
        try {
            DecodedJWT decodedJWT = decodeToken(token);
            long expirationTime = decodedJWT.getClaim("exp").asLong();

            long currentTime = System.currentTimeMillis() / 1000;

            return expirationTime < currentTime;
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isAdminFromToken(String token) {
        try {
            DecodedJWT decodedJWT = decodeToken(token);
            return decodedJWT.getClaim("isAdmin").asBoolean();
        } catch (Exception e) {
            return false;
        }

    }
}
