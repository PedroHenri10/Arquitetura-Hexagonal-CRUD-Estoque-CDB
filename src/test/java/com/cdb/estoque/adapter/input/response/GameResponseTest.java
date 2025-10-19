package com.cdb.estoque.adapter.input.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameResponseTest {

    @Test
    void testNoArgsConstructor() {
        GameResponse response = new GameResponse();
        assertNotNull(response);
        assertNull(response.getId());
        assertNull(response.getTitleGame());
    }

    @Test
    void testAllArgsConstructor() {
        GameResponse response = new GameResponse(1L, "Test Game", "PC", "Action", 59.99, 10);
        assertEquals(1L, response.getId());
        assertEquals("Test Game", response.getTitleGame());
        assertEquals("PC", response.getPlataform());
        assertEquals("Action", response.getGenre());
        assertEquals(59.99, response.getPrice(), 0.001);
        assertEquals(10, response.getStock());
    }

    @Test
    void testBuilder() {
        GameResponse response = GameResponse.builder()
                .id(2L)
                .titleGame("Another Game")
                .plataform("PS5")
                .genre("Adventure")
                .price(69.99)
                .stock(5)
                .build();

        assertEquals(2L, response.getId());
        assertEquals("Another Game", response.getTitleGame());
        assertEquals("PS5", response.getPlataform());
        assertEquals("Adventure", response.getGenre());
        assertEquals(69.99, response.getPrice(), 0.001);
        assertEquals(5, response.getStock());
    }

    @Test
    void testGettersAndSetters() {
        GameResponse response = new GameResponse();
        response.setId(3L);
        response.setTitleGame("Set Game");
        response.setPlataform("Xbox");
        response.setGenre("RPG");
        response.setPrice(49.99);
        response.setStock(20);

        assertEquals(3L, response.getId());
        assertEquals("Set Game", response.getTitleGame());
        assertEquals("Xbox", response.getPlataform());
        assertEquals("RPG", response.getGenre());
        assertEquals(49.99, response.getPrice(), 0.001);
        assertEquals(20, response.getStock());
    }

    @Test
    void testEqualsAndHashCode() {
        GameResponse response1 = GameResponse.builder().id(1L).titleGame("Game").build();
        GameResponse response2 = GameResponse.builder().id(1L).titleGame("Game").build();
        GameResponse response3 = GameResponse.builder().id(2L).titleGame("Game").build();

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }
}