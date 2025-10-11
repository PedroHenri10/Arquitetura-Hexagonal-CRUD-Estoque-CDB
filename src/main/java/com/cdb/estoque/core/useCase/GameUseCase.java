    package com.cdb.estoque.core.useCase;

import com.cdb.estoque.core.domain.model.Game;
import com.cdb.estoque.core.domain.strategy.DecreaseStockOperation;
import com.cdb.estoque.core.domain.strategy.IncreaseStockOperation;
import com.cdb.estoque.exception.ResourceNotFoundException;
import com.cdb.estoque.port.input.GameInputPort;
import com.cdb.estoque.port.output.GameRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class GameUseCase implements GameInputPort {

    private final GameRepositoryPort gameRepositoryPort;
    private final IncreaseStockOperation increaseStockOperation;
    private final DecreaseStockOperation decreaseStockOperation;

    public GameUseCase(GameRepositoryPort gameRepositoryPort,
                       IncreaseStockOperation increaseStockOperation,
                       DecreaseStockOperation decreaseStockOperation) {
        this.gameRepositoryPort = gameRepositoryPort;
        this.increaseStockOperation = increaseStockOperation;
        this.decreaseStockOperation = decreaseStockOperation;
    }

    @Override
    public List<Game> findAll() {
        return gameRepositoryPort.findAll();
    }

    @Override
    public Optional<Game> findById(Long id) {
        return gameRepositoryPort.findById(id);
    }

    @Override
    public Game save(Game game) {
        return gameRepositoryPort.save(game);
    }

    @Override
    public Game update(Long id, Game game) {
        gameRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));
        game.setId(id);
        return gameRepositoryPort.save(game);
    }

    @Override
    @Transactional
    public Game increaseStock(Long id, int quantity) {
        Game game = gameRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));
        increaseStockOperation.execute(game, quantity); 
        return gameRepositoryPort.save(game);
    }

    @Override
    @Transactional
    public Game decreaseStock(Long id, int quantity) {
        Game game = gameRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));
        decreaseStockOperation.execute(game, quantity); 
        return gameRepositoryPort.save(game);
    }

    @Override
    public List<Game> findByTitleGameContainingIgnoreCase(String titleGame) {
        List<Game> games = gameRepositoryPort.findByTitleGameContainingIgnoreCase(titleGame);
        if (games.isEmpty()) {
            throw new ResourceNotFoundException("Game not found");
        }
        return games;
    }

    @Override
    public List<Game> findByGenreContainingIgnoreCase(String genre) {
        List<Game> games = gameRepositoryPort.findByGenreContainingIgnoreCase(genre);
        if (games.isEmpty()) {
            throw new ResourceNotFoundException("Game not found");
        }
        return games;
    }

    @Override
    public List<Game> findByPlataformContainingIgnoreCase(String plataform) {
        List<Game> games = gameRepositoryPort.findByPlataformContainingIgnoreCase(plataform);
        if (games.isEmpty()) {
            throw new ResourceNotFoundException("Game not found");
        }
        return games;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!gameRepositoryPort.existsById(id)) {
            throw new ResourceNotFoundException("Game not found");
        }
        gameRepositoryPort.deleteById(id);
    }
}
