package com.fiap.burguer.core.application.ports;

public interface AuthenticationPort {
    void validateAuthorizationHeader(String authorizationHeader);
    Boolean validateIsTokenExpired(String authorizationHeader);
    void validateIsAdminAccess(String authorizationHeader);
    Integer validateIdUser(String authorizationHeader);
}
