package com.cdb.estoque.core.domain.decorator;

import com.cdb.estoque.core.domain.model.Game;
import com.cdb.estoque.factory.GameFactoryBot;
import com.cdb.estoque.port.input.GameInputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IdValidationGameUseCaseDecoratorTest {

    @Mock
    private GameInputPort decoratedGameUseCase;

    private IdValidationGameUseCaseDecorator decorator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        decorator = new IdValidationGameUseCaseDecorator(decoratedGameUseCase);
    }

    @Test
    void findById_shouldValidateIdAndDelegateWhenValid() {
        Long validId = 1L;
        Game game = GameFactoryBot.createGameWithId(validId);
        when(decoratedGameUseCase.findById(validId)).thenReturn(Optional.of(game));

        Optional<Game> result = decorator.findById(validId);

        assertTrue(result.isPresent());
        assertEquals(game, result.get());
        verify(decoratedGameUseCase, times(1)).findById(validId);
    }

    @Test
    void findById_shouldThrowExceptionWhenIdIsNull() {
        assertThatThrownBy(() -> decorator.findById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The game ID shoud be positive value and not null.");
        verifyNoInteractions(decoratedGameUseCase);
    }

    @Test
    void findById_shouldThrowExceptionWhenIdIsZero() {
        assertThatThrownBy(() -> decorator.findById(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The game ID shoud be positive value and not null.");
        verifyNoInteractions(decoratedGameUseCase);
    }

    @Test
    void findById_shouldThrowExceptionWhenIdIsNegative() {
        assertThatThrownBy(() -> decorator.findById(-1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The game ID shoud be positive value and not null.");
        verifyNoInteractions(decoratedGameUseCase);
    }

    @Test
    void update_shouldValidateIdAndDelegateWhenValid() {
        Long validId = 1L;
        Game game = GameFactoryBot.createDefaultGame();
        Game updatedGame = GameFactoryBot.createGameWithId(validId);
        when(decoratedGameUseCase.update(validId, game)).thenReturn(updatedGame);

        Game result = decorator.update(validId, game);

        assertEquals(updatedGame, result);
        verify(decoratedGameUseCase, times(1)).update(validId, game);
    }

    @Test
    void update_shouldThrowExceptionWhenIdIsNull() {
        Game game = GameFactoryBot.createDefaultGame();
        assertThatThrownBy(() -> decorator.update(null, game))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The game ID shoud be positive value and not null.");
        verifyNoInteractions(decoratedGameUseCase);
    }

    @Test
    void increaseStock_shouldValidateIdAndDelegateWhenValid() {
        Long validId = 1L;
        int quantity = 5;
        Game game = GameFactoryBot.createGameWithId(validId);
        when(decoratedGameUseCase.increaseStock(validId, quantity)).thenReturn(game);

        Game result = decorator.increaseStock(validId, quantity);

        assertEquals(game, result);
        verify(decoratedGameUseCase, times(1)).increaseStock(validId, quantity);
    }

    @Test
    void increaseStock_shouldThrowExceptionWhenIdIsNegative() {
        assertThatThrownBy(() -> decorator.increaseStock(-1L, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The game ID shoud be positive value and not null.");
        verifyNoInteractions(decoratedGameUseCase);
    }

    @Test
    void decreaseStock_shouldValidateIdAndDelegateWhenValid() {
        Long validId = 1L;
        int quantity = 3;
        Game game = GameFactoryBot.createGameWithId(validId);
        when(decoratedGameUseCase.decreaseStock(validId, quantity)).thenReturn(game);

        Game result = decorator.decreaseStock(validId, quantity);

        assertEquals(game, result);
        verify(decoratedGameUseCase, times(1)).decreaseStock(validId, quantity);
    }

    @Test
    void decreaseStock_shouldThrowExceptionWhenIdIsZero() {
        assertThatThrownBy(() -> decorator.decreaseStock(0L, 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The game ID shoud be positive value and not null.");
        verifyNoInteractions(decoratedGameUseCase);
    }

    @Test
    void deleteById_shouldValidateIdAndDelegateWhenValid() {
        Long validId = 1L;
        doNothing().when(decoratedGameUseCase).deleteById(validId);

        decorator.deleteById(validId);

        verify(decoratedGameUseCase, times(1)).deleteById(validId);
    }

    @Test
    void deleteById_shouldThrowExceptionWhenIdIsNull() {
        assertThatThrownBy(() -> decorator.deleteById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The game ID shoud be positive value and not null.");
        verifyNoInteractions(decoratedGameUseCase);
    }

    @Test
    void findAll_shouldDelegateDirectly() {
        List<Game> games = Collections.singletonList(GameFactoryBot.createDefaultGame());
        when(decoratedGameUseCase.findAll()).thenReturn(games);

        List<Game> result = decorator.findAll();

        assertEquals(games, result);
        verify(decoratedGameUseCase, times(1)).findAll();
        verifyNoMoreInteractions(decoratedGameUseCase);
    }

    @Test
    void save_shouldDelegateDirectly() {
        Game game = GameFactoryBot.createDefaultGame();
        Game savedGame = GameFactoryBot.createGameWithId(1L);
        when(decoratedGameUseCase.save(game)).thenReturn(savedGame);

        Game result = decorator.save(game);

        assertEquals(savedGame, result);
        verify(decoratedGameUseCase, times(1)).save(game);
        verifyNoMoreInteractions(decoratedGameUseCase);
    }

    @Test
    void findByTitleGameContainingIgnoreCase_shouldDelegateDirectly() {
        String title = "test";
        List<Game> games = Collections.singletonList(GameFactoryBot.createDefaultGame());
        when(decoratedGameUseCase.findByTitleGameContainingIgnoreCase(title)).thenReturn(games);

        List<Game> result = decorator.findByTitleGameContainingIgnoreCase(title);

        assertEquals(games, result);
        verify(decoratedGameUseCase, times(1)).findByTitleGameContainingIgnoreCase(title);
        verifyNoMoreInteractions(decoratedGameUseCase);
    }

    @Test
    void findByGenreContainingIgnoreCase_shouldDelegateDirectly() {
        String genre = "action";
        List<Game> games = Collections.singletonList(GameFactoryBot.createDefaultGame());
        when(decoratedGameUseCase.findByGenreContainingIgnoreCase(genre)).thenReturn(games);

        List<Game> result = decorator.findByGenreContainingIgnoreCase(genre);

        assertEquals(games, result);
        verify(decoratedGameUseCase, times(1)).findByGenreContainingIgnoreCase(genre);
        verifyNoMoreInteractions(decoratedGameUseCase);
    }

    @Test
    void findByPlataformContainingIgnoreCase_shouldDelegateDirectly() {
        String platform = "PC";
        List<Game> games = Collections.singletonList(GameFactoryBot.createDefaultGame());
        when(decoratedGameUseCase.findByPlataformContainingIgnoreCase(platform)).thenReturn(games);

        List<Game> result = decorator.findByPlataformContainingIgnoreCase(platform);

        assertEquals(games, result);
        verify(decoratedGameUseCase, times(1)).findByPlataformContainingIgnoreCase(platform);
        verifyNoMoreInteractions(decoratedGameUseCase);
    }
}