package com.cdb.estoque.core.userCase;

import com.cdb.estoque.adapter.input.request.GameRequest;
import com.cdb.estoque.adapter.output.entity.Game;
import com.cdb.estoque.exception.ResourceNotFoundException;
import com.cdb.estoque.adapter.output.repository.GameRepository;
import com.cdb.estoque.port.input.GameInputPort;
import com.cdb.estoque.port.output.GameRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameUserCase implements GameInputPort {

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

        game.setId();
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
