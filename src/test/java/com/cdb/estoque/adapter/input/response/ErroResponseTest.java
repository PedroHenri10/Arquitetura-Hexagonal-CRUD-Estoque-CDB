package com.cdb.estoque.adapter.input.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ErroResponseTest {

    @Test
    void testNoArgsConstructor() {
        ErroResponse response = new ErroResponse();
        assertNotNull(response);
        assertNull(response.getTimestamp());
        assertNull(response.getStatus());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        ErroResponse response = new ErroResponse(now, 400, "Bad Request", "Invalid input", "/api/data");
        assertEquals(now, response.getTimestamp());
        assertEquals(400, response.getStatus());
        assertEquals("Bad Request", response.getError());
        assertEquals("Invalid input", response.getMessage());
        assertEquals("/api/data", response.getPath());
    }

    @Test
    void testBuilder() {
        LocalDateTime now = LocalDateTime.now();
        ErroResponse response = ErroResponse.builder()
                .timestamp(now)
                .status(404)
                .error("Not Found")
                .message("Resource not found")
                .path("/api/resource/1")
                .build();

        assertEquals(now, response.getTimestamp());
        assertEquals(404, response.getStatus());
        assertEquals("Not Found", response.getError());
        assertEquals("Resource not found", response.getMessage());
        assertEquals("/api/resource/1", response.getPath());
    }

    @Test
    void testGettersAndSetters() {
        LocalDateTime now = LocalDateTime.now();
        ErroResponse response = new ErroResponse();
        response.setTimestamp(now);
        response.setStatus(500);
        response.setError("Internal Server Error");
        response.setMessage("Something went wrong");
        response.setPath("/api/error");

        assertEquals(now, response.getTimestamp());
        assertEquals(500, response.getStatus());
        assertEquals("Internal Server Error", response.getError());
        assertEquals("Something went wrong", response.getMessage());
        assertEquals("/api/error", response.getPath());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        ErroResponse response1 = ErroResponse.builder().timestamp(now).status(400).build();
        ErroResponse response2 = ErroResponse.builder().timestamp(now).status(400).build();
        ErroResponse response3 = ErroResponse.builder().timestamp(now.plusSeconds(1)).status(400).build();

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);

        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }
}