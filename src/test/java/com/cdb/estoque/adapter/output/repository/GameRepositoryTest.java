package com.cdb.estoque.adapter.output.repository;

import com.cdb.estoque.adapter.output.entity.GameEntity;
import com.cdb.estoque.adapter.output.mapper.GamePersistanceMapper;
import com.cdb.estoque.adapter.output.persistence.DataGameRepository;
import com.cdb.estoque.adapter.output.persistence.GamePersistenceAdapter;
import com.cdb.estoque.core.domain.model.Game;
import com.cdb.estoque.factory.GameFactoryBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class GameRepositoryTest {
    @Mock
    private DataGameRepository dataGameRepository;

    @Mock
    private GamePersistanceMapper mapper;

    @InjectMocks
    private GamePersistenceAdapter adapter;

    private Game game;
    private GameEntity entity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        game = GameFactoryBot.createGameWithId(1L);
        entity = new GameEntity(1L, "Zelda", "Switch", "Aventura", 250.0, 5);
    }


    @Test
    void testFindAll() {
        when(dataGameRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(game);

        List<Game> result = adapter.findAll();

        assertEquals(1, result.size());
        assertEquals("Zelda", result.get(0).getTitleGame());
        verify(dataGameRepository).findAll();
        verify(mapper).toDomain(entity);
    }

    @Test
    void testFindById() {
        when(dataGameRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(game);

        Optional<Game> result = adapter.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Zelda", result.get().getTitleGame());
        verify(dataGameRepository).findById(1L);
        verify(mapper).toDomain(entity);
    }

    @Test
    void testSave() {
        when(mapper.toEntity(game)).thenReturn(entity);
        when(dataGameRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(game);

        Game result = adapter.save(game);

        assertEquals("Zelda", result.getTitleGame());
        verify(dataGameRepository).save(entity);
        verify(mapper).toEntity(game);
        verify(mapper).toDomain(entity);
    }

    @Test
    void testUpdate() {
        game.setId(1L);
        when(mapper.toEntity(game)).thenReturn(entity);
        when(dataGameRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(game);

        Game result = adapter.update(1L, game);

        assertEquals("Zelda", result.getTitleGame());
        verify(dataGameRepository).save(entity);
        verify(mapper).toEntity(game);
        verify(mapper).toDomain(entity);
    }

    @Test
    void testFindByTitleGameContainingIgnoreCase() {
        when(dataGameRepository.findByTitleGameContainingIgnoreCase("Zelda")).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(game);

        List<Game> result = adapter.findByTitleGameContainingIgnoreCase("Zelda");

        assertEquals(1, result.size());
        assertEquals("Zelda", result.get(0).getTitleGame());
        verify(dataGameRepository).findByTitleGameContainingIgnoreCase("Zelda");
        verify(mapper).toDomain(entity);
    }

    @Test
    void testFindByGenreContainingIgnoreCase() {
        when(dataGameRepository.findByGenreContainingIgnoreCase("Aventura")).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(game);

        List<Game> result = adapter.findByGenreContainingIgnoreCase("Aventura");

        assertEquals(1, result.size());
        assertEquals("Aventura", result.get(0).getGenre());
        verify(dataGameRepository).findByGenreContainingIgnoreCase("Aventura");
        verify(mapper).toDomain(entity);
    }

    @Test
    void testFindByPlataformContainingIgnoreCase() {
        when(dataGameRepository.findByPlataformContainingIgnoreCase("Switch")).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(game);

        List<Game> result = adapter.findByPlataformContainingIgnoreCase("Switch");

        assertEquals(1, result.size());
        assertEquals("Switch", result.get(0).getPlataform());
        verify(dataGameRepository).findByPlataformContainingIgnoreCase("Switch");
        verify(mapper).toDomain(entity);
    }

    @Test
    void testDeleteById() {
        doNothing().when(dataGameRepository).deleteById(1L);

        adapter.deleteById(1L);

        verify(dataGameRepository).deleteById(1L);
    }

    @Test
    void testExistsById() {
        when(dataGameRepository.existsById(1L)).thenReturn(true);

        boolean result = adapter.existsById(1L);

        assertTrue(result);
        verify(dataGameRepository).existsById(1L);
    }


}
