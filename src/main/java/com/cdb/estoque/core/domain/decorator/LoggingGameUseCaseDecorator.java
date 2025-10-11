package com.cdb.estoque.core.domain.decorator;

import com.cdb.estoque.core.domain.model.Game;
import com.cdb.estoque.port.input.GameInputPort;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class LoggingGameUseCaseDecorator implements GameInputPort {

    private final GameInputPort decoratedGameUseCase;

    public LoggingGameUseCaseDecorator(GameInputPort decoratedGameUseCase) {
        this.decoratedGameUseCase = decoratedGameUseCase;
    }

    @Override
    public List<Game> findAll() {
        log.info("Starting search for all games.");
        List<Game> games = decoratedGameUseCase.findAll();
        log.info("Search for all games completed. Total of {} games found.", games.size());
        return games;
    }

    @Override
    public Optional<Game> findById(Long id) {
        log.info("Starting search for game with ID: {}", id);
        Optional<Game> game = decoratedGameUseCase.findById(id);
        if (game.isPresent()) {
            log.info("Game with ID {} found: {}", id, game.get().getTitleGame());
        } else {
            log.warn("Game with ID {} not found.", id);
        }
        return game;
    }

    @Override
    public Game save(Game game) {
        log.info("Starting to save game: {}", game.getTitleGame());
        Game savedGame = decoratedGameUseCase.save(game);
        log.info("Game saved successfully. ID: {}", savedGame.getId());
        return savedGame;
    }

    @Override
    public Game update(Long id, Game game) {
        log.info("Starting update for game with ID: {}", id);
        Game updatedGame = decoratedGameUseCase.update(id, game);
        log.info("Game with ID {} updated successfully.", id);
        return updatedGame;
    }

    @Override
    public Game increaseStock(Long id, int quantity) {
        log.info("Starting stock increase for game ID: {} with quantity: {}", id, quantity);
        Game game = decoratedGameUseCase.increaseStock(id, quantity);
        log.info("Stock for game ID {} increased to: {}", id, game.getStock());
        return game;
    }

    @Override
    public Game decreaseStock(Long id, int quantity) {
        log.info("Starting stock decrease for game ID: {} with quantity: {}", id, quantity);
        Game game = decoratedGameUseCase.decreaseStock(id, quantity);
        log.info("Stock for game ID {} decreased to: {}", id, game.getStock());
        return game;
    }

    @Override
    public List<Game> findByTitleGameContainingIgnoreCase(String titleGame) {
        log.info("Starting search for games with title containing: {}", titleGame);
        List<Game> games = decoratedGameUseCase.findByTitleGameContainingIgnoreCase(titleGame);
        log.info("Search by title '{}' completed. Total of {} games found.", titleGame, games.size());
        return games;
    }

    @Override
    public List<Game> findByGenreContainingIgnoreCase(String genre) {
        log.info("Starting search for games with genre containing: {}", genre);
        List<Game> games = decoratedGameUseCase.findByGenreContainingIgnoreCase(genre);
        log.info("Search by genre '{}' completed. Total of {} games found.", genre, games.size());
        return games;
    }

    @Override
    public List<Game> findByPlataformContainingIgnoreCase(String plataform) {
        log.info("Starting search for games with platform containing: {}", plataform);
        List<Game> games = decoratedGameUseCase.findByPlataformContainingIgnoreCase(plataform);
        log.info("Search by platform '{}' completed. Total of {} games found.", plataform, games.size());
        return games;
    }

    @Override
    public void deleteById(Long id) {
        log.info("Starting deletion of game with ID: {}", id);
        decoratedGameUseCase.deleteById(id);
        log.info("Game with ID {} deleted successfully.", id);
    }
}