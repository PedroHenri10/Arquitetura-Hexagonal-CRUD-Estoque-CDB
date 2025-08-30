package com.cdb.estoque.core.useCase;

import com.cdb.estoque.core.domain.model.Game;
import com.cdb.estoque.exception.ResourceNotFoundException;
import com.cdb.estoque.port.input.GameInputPort;
import com.cdb.estoque.port.output.GameRepositoryPort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameUseCase implements GameInputPort {

    @Autowired
    private final GameRepositoryPort gameRepositoryPort;

    @Override
    public List<Game> findAll(){
        return gameRepositoryPort.findAll();
    }

    @Override
    public Optional<Game> findById(Long id){
        return gameRepositoryPort.findById(id);
    }

    @Override
    public Game save(Game game){
        return gameRepositoryPort.save(game);
    }

    @Override
    public Game update(Long id, Game game){
        gameRepositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game not found"));

        game.setId(id);
        return gameRepositoryPort.save(game);
    }

    @Override
    @Transactional
    public Game increaseStock(Long id, int quantity) {
        Game game = gameRepositoryPort.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
        game.increaseStock(quantity);
        return gameRepositoryPort.save(game);
    }

    @Override
    @Transactional
    public Game decreaseStock(Long id, int quantity) {
        Game game = gameRepositoryPort.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
        game.decreaseStock(quantity);
        return gameRepositoryPort.save(game);

    }

    @Override
    @Transactional
    public void deleteById(Long id){
        if(!gameRepositoryPort.existsById(id)){
            throw new ResourceNotFoundException("Game not found");
        }
        gameRepositoryPort.deleteById(id);
    }
}
