package com.cdb.estoque.core.domain.decorator;

import com.cdb.estoque.core.domain.model.Game;
import com.cdb.estoque.factory.GameFactoryBot;
import com.cdb.estoque.port.input.GameInputPort;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoggingGameUseCaseDecoratorTest {

    @Mock
    private GameInputPort decoratedGameUseCase;

    private LoggingGameUseCaseDecorator decorator;
    private ListAppender<ILoggingEvent> listAppender;
    private Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        decorator = new LoggingGameUseCaseDecorator(decoratedGameUseCase);

        logger = (Logger) LoggerFactory.getLogger(LoggingGameUseCaseDecorator.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
        logger.setLevel(Level.INFO);
    }

    @Test
    void findAll_shouldLogAndDelegate() {
        List<Game> games = Collections.singletonList(GameFactoryBot.createDefaultGame());
        when(decoratedGameUseCase.findAll()).thenReturn(games);

        List<Game> result = decorator.findAll();

        assertEquals(games, result);
        verify(decoratedGameUseCase, times(1)).findAll();

        assertEquals(2, listAppender.list.size());
        assertEquals("Starting search for all games.", listAppender.list.get(0).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(0).getLevel());
        assertEquals("Search for all games completed. Total of 1 games found.", listAppender.list.get(1).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(1).getLevel());
    }

    @Test
    void findById_shouldLogAndDelegateWhenFound() {
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);
        when(decoratedGameUseCase.findById(id)).thenReturn(Optional.of(game));

        Optional<Game> result = decorator.findById(id);

        assertTrue(result.isPresent());
        assertEquals(game, result.get());
        verify(decoratedGameUseCase, times(1)).findById(id);

        assertEquals(2, listAppender.list.size());
        assertEquals("Starting search for game with ID: 1", listAppender.list.get(0).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(0).getLevel());
        assertEquals("Game with ID 1 found: Zelda", listAppender.list.get(1).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(1).getLevel());
    }

    @Test
    void findById_shouldLogAndDelegateWhenNotFound() {
        Long id = 99L;
        when(decoratedGameUseCase.findById(id)).thenReturn(Optional.empty());

        Optional<Game> result = decorator.findById(id);

        assertFalse(result.isPresent());
        verify(decoratedGameUseCase, times(1)).findById(id);

        assertEquals(2, listAppender.list.size());
        assertEquals("Starting search for game with ID: 99", listAppender.list.get(0).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(0).getLevel());
        assertEquals("Game with ID 99 not found.", listAppender.list.get(1).getMessage());
        assertEquals(Level.WARN, listAppender.list.get(1).getLevel());
    }

    @Test
    void save_shouldLogAndDelegate() {
        Game gameToSave = GameFactoryBot.createDefaultGame();
        Game savedGame = GameFactoryBot.createGameWithId(1L);
        when(decoratedGameUseCase.save(gameToSave)).thenReturn(savedGame);

        Game result = decorator.save(gameToSave);

        assertEquals(savedGame, result);
        verify(decoratedGameUseCase, times(1)).save(gameToSave);

        assertEquals(2, listAppender.list.size());
        assertEquals("Starting to save game: Jogo Teste", listAppender.list.get(0).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(0).getLevel());
        assertEquals("Game saved successfully. ID: 1", listAppender.list.get(1).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(1).getLevel());
    }

    @Test
    void update_shouldLogAndDelegate() {
        Long id = 1L;
        Game gameToUpdate = GameFactoryBot.createDefaultGame();
        Game updatedGame = GameFactoryBot.createGameWithId(id);
        when(decoratedGameUseCase.update(id, gameToUpdate)).thenReturn(updatedGame);

        Game result = decorator.update(id, gameToUpdate);

        assertEquals(updatedGame, result);
        verify(decoratedGameUseCase, times(1)).update(id, gameToUpdate);

        assertEquals(2, listAppender.list.size());
        assertEquals("Starting update for game with ID: 1", listAppender.list.get(0).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(0).getLevel());
        assertEquals("Game with ID 1 updated successfully.", listAppender.list.get(1).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(1).getLevel());
    }

    @Test
    void increaseStock_shouldLogAndDelegate() {
        Long id = 1L;
        int quantity = 5;
        Game gameAfterIncrease = GameFactoryBot.createGameWithId(id);
        gameAfterIncrease.setStock(15);
        when(decoratedGameUseCase.increaseStock(id, quantity)).thenReturn(gameAfterIncrease);

        Game result = decorator.increaseStock(id, quantity);

        assertEquals(gameAfterIncrease, result);
        verify(decoratedGameUseCase, times(1)).increaseStock(id, quantity);

        assertEquals(2, listAppender.list.size());
        assertEquals("Starting stock increase for game ID: 1 with quantity: 5", listAppender.list.get(0).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(0).getLevel());
        assertEquals("Stock for game ID 1 increased to: 15", listAppender.list.get(1).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(1).getLevel());
    }

    @Test
    void decreaseStock_shouldLogAndDelegate() {
        Long id = 1L;
        int quantity = 3;
        Game gameAfterDecrease = GameFactoryBot.createGameWithId(id);
        gameAfterDecrease.setStock(7);
        when(decoratedGameUseCase.decreaseStock(id, quantity)).thenReturn(gameAfterDecrease);

        Game result = decorator.decreaseStock(id, quantity);

        assertEquals(gameAfterDecrease, result);
        verify(decoratedGameUseCase, times(1)).decreaseStock(id, quantity);

        assertEquals(2, listAppender.list.size());
        assertEquals("Starting stock decrease for game ID: 1 with quantity: 3", listAppender.list.get(0).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(0).getLevel());
        assertEquals("Stock for game ID 1 decreased to: 7", listAppender.list.get(1).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(1).getLevel());
    }

    @Test
    void findByTitleGameContainingIgnoreCase_shouldLogAndDelegate() {
        String title = "zelda";
        List<Game> games = Collections.singletonList(GameFactoryBot.createGameWithId(1L));
        when(decoratedGameUseCase.findByTitleGameContainingIgnoreCase(title)).thenReturn(games);

        List<Game> result = decorator.findByTitleGameContainingIgnoreCase(title);

        assertEquals(games, result);
        verify(decoratedGameUseCase, times(1)).findByTitleGameContainingIgnoreCase(title);

        assertEquals(2, listAppender.list.size());
        assertEquals("Starting search for games with title containing: zelda", listAppender.list.get(0).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(0).getLevel());
        assertEquals("Search by title 'zelda' completed. Total of 1 games found.", listAppender.list.get(1).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(1).getLevel());
    }

    @Test
    void findByGenreContainingIgnoreCase_shouldLogAndDelegate() {
        String genre = "adventure";
        List<Game> games = Collections.singletonList(GameFactoryBot.createGameWithId(1L));
        when(decoratedGameUseCase.findByGenreContainingIgnoreCase(genre)).thenReturn(games);

        List<Game> result = decorator.findByGenreContainingIgnoreCase(genre);

        assertEquals(games, result);
        verify(decoratedGameUseCase, times(1)).findByGenreContainingIgnoreCase(genre);

        assertEquals(2, listAppender.list.size());
        assertEquals("Starting search for games with genre containing: adventure", listAppender.list.get(0).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(0).getLevel());
        assertEquals("Search by genre 'adventure' completed. Total of 1 games found.", listAppender.list.get(1).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(1).getLevel());
    }

    @Test
    void findByPlataformContainingIgnoreCase_shouldLogAndDelegate() {
        String plataform = "switch";
        List<Game> games = Collections.singletonList(GameFactoryBot.createGameWithId(1L));
        when(decoratedGameUseCase.findByPlataformContainingIgnoreCase(plataform)).thenReturn(games);

        List<Game> result = decorator.findByPlataformContainingIgnoreCase(plataform);

        assertEquals(games, result);
        verify(decoratedGameUseCase, times(1)).findByPlataformContainingIgnoreCase(plataform);

        assertEquals(2, listAppender.list.size());
        assertEquals("Starting search for games with platform containing: switch", listAppender.list.get(0).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(0).getLevel());
        assertEquals("Search by platform 'switch' completed. Total of 1 games found.", listAppender.list.get(1).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(1).getLevel());
    }

    @Test
    void deleteById_shouldLogAndDelegate() {
        Long id = 1L;
        doNothing().when(decoratedGameUseCase).deleteById(id);

        decorator.deleteById(id);

        verify(decoratedGameUseCase, times(1)).deleteById(id);

        assertEquals(2, listAppender.list.size());
        assertEquals("Starting deletion of game with ID: 1", listAppender.list.get(0).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(0).getLevel());
        assertEquals("Game with ID 1 deleted successfully.", listAppender.list.get(1).getMessage());
        assertEquals(Level.INFO, listAppender.list.get(1).getLevel());
    }
}