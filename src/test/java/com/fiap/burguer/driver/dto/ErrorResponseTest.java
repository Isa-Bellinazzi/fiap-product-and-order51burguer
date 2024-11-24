package com.fiap.burguer.driver.dto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ErrorResponseTest {

    @Test
    public void testConstructorAndGetters() {
        int status = 404;
        String message = "Not Found";
        long timestamp = System.currentTimeMillis();
        ErrorResponse errorResponse = new ErrorResponse(status, message, timestamp);
        assertEquals(status, errorResponse.getStatus());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(timestamp, errorResponse.getTimestamp());
    }

    @Test
    public void testSetters() {
        ErrorResponse errorResponse = new ErrorResponse(0, "", 0);
        int status = 500;
        String message = "Internal Server Error";
        long timestamp = System.currentTimeMillis();
        errorResponse.setStatus(status);
        errorResponse.setMessage(message);
        errorResponse.setTimestamp(timestamp);
        assertEquals(status, errorResponse.getStatus());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(timestamp, errorResponse.getTimestamp());
    }

    @Test
    public void testErrorResponseDefaultConstructor() {
        ErrorResponse errorResponse = new ErrorResponse(0, "", 0);
        assertEquals(0, errorResponse.getStatus());
        assertEquals("", errorResponse.getMessage());
        assertEquals(0, errorResponse.getTimestamp());
    }
}
