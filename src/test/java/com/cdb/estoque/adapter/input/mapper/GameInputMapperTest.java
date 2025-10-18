package com.cdb.estoque.adapter.input.mapper;

import com.cdb.estoque.adapter.input.request.GameRequest;
import com.cdb.estoque.adapter.input.response.GameResponse;
import com.cdb.estoque.core.domain.model.Game;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameInputMapperTest {

    private final GameRestMapper mapper = Mappers.getMapper(GameRestMapper.class);

    @Test
    void testToDomain() {
        GameRequest request = new GameRequest("Jogo Teste", "PC", "Ação", 150.0, 10);
        Game game = mapper.toDomain(request);

        assertEquals(request.getTitleGame(), game.getTitleGame());
        assertEquals(request.getPlataform(), game.getPlataform());
        assertEquals(request.getGenre(), game.getGenre());
        assertEquals(request.getPrice(), game.getPrice());
        assertEquals(request.getStock(), game.getStock());
    }

    @Test
    void testToResponse() {
        Game game = new Game(1L, "Jogo Teste", "PC", "Ação", 150.0, 10);
        GameResponse response = mapper.toResponse(game);

        assertEquals(game.getId(), response.getId());
        assertEquals(game.getTitleGame(), response.getTitleGame());
        assertEquals(game.getPlataform(), response.getPlataform());
        assertEquals(game.getGenre(), response.getGenre());
        assertEquals(game.getPrice(), response.getPrice());
        assertEquals(game.getStock(), response.getStock());
    }

    @Test
    void testToDomain_null() {
        Game game = mapper.toDomain(null);
        assertEquals(null, game);
    }

    @Test
    void testToResponse_null() {
        GameResponse response = mapper.toResponse(null);
        assertEquals(null, response);
    }
}