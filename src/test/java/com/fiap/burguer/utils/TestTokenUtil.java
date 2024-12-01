package com.fiap.burguer.utils;

import java.util.Base64;
import java.util.Map;

public class TestTokenUtil {

    public static String generateValidMockToken(Map<String, Object> payload) {
        String header = Base64.getUrlEncoder().encodeToString("{\"typ\":\"JWT\",\"alg\":\"HS256\"}".getBytes());
        String body = Base64.getUrlEncoder().encodeToString(buildPayload(payload).getBytes());
        String signature = "mockSignature";

        return "Bearer " + header + "." + body + "." + signature;
    }

    private static String buildPayload(Map<String, Object> payload) {
        StringBuilder json = new StringBuilder("{");
        payload.forEach((key, value) -> {
            if (value instanceof String) {
                json.append("\"").append(key).append("\":\"").append(value).append("\",");
            } else {
                json.append("\"").append(key).append("\":").append(value).append(",");
            }
        });
        if (json.length() > 1) {
            json.deleteCharAt(json.length() - 1);
        }
        json.append("}");
        return json.toString();
    }
}