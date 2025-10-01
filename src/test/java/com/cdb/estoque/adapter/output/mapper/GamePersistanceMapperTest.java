package com.cdb.estoque.adapter.output.mapper;

import com.cdb.estoque.adapter.output.entity.GameEntity;
import com.cdb.estoque.adapter.output.mapper.GamePersistanceMapper;
import com.cdb.estoque.core.domain.model.Game;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GamePersistanceMapperTest {

    private final GamePersistanceMapper mapper = Mappers.getMapper(GamePersistanceMapper.class);

    @Test
    void testToEntity() {
        Game game = new Game(1L, "Jogo Teste", "PC", "Ação", 150.0, 10);
        GameEntity entity = mapper.toEntity(game);

        assertEquals(game.getId(), entity.getId());
        assertEquals(game.getTitleGame(), entity.getTitleGame());
        assertEquals(game.getPlataform(), entity.getPlataform());
        assertEquals(game.getGenre(), entity.getGenre());
        assertEquals(game.getPrice(), entity.getPrice());
        assertEquals(game.getStock(), entity.getStock());
    }

    @Test
    void testToDomain() {
        GameEntity entity = new GameEntity();
        entity.setId(1L);
        entity.setTitleGame("Jogo Teste");
        entity.setPlataform("PC");
        entity.setGenre("Ação");
        entity.setPrice(150.0);
        entity.setStock(10);

        Game game = mapper.toDomain(entity);

        assertEquals(entity.getId(), game.getId());
        assertEquals(entity.getTitleGame(), game.getTitleGame());
        assertEquals(entity.getPlataform(), game.getPlataform());
        assertEquals(entity.getGenre(), game.getGenre());
        assertEquals(entity.getPrice(), game.getPrice());
        assertEquals(entity.getStock(), game.getStock());
    }
}
