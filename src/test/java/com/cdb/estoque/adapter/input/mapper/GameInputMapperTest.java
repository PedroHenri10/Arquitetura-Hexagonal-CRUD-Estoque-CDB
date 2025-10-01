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

        assertEquals(game.getId(), response.id());
        assertEquals(game.getTitleGame(), response.titleGame());
        assertEquals(game.getPlataform(), response.plataform());
        assertEquals(game.getGenre(), response.genre());
        assertEquals(game.getPrice(), response.price());
        assertEquals(game.getStock(), response.stock());
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
