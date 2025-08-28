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

    @Transactional
    public GameRequest increaseStock(Long id, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive.");
        return repository.findById(id)
                .map(game -> {
                    game.setStock(game.getStock() + quantity);
                    return convertToDTO(repository.save(game));
                })
                .orElseThrow(() -> new RuntimeException("Game not found"));
    }

    @Transactional
    public GameRequest decreaseStock(Long id, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive.");
        return repository.findById(id)
                .map(game -> {
                    if (game.getStock() < quantity) {
                        throw new IllegalArgumentException("Not enough stock.");
                    }
                    game.setStock(game.getStock() - quantity);
                    return convertToDTO(repository.save(game));
                })
                .orElseThrow(() -> new RuntimeException("Game not found"));
    }

    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Game not found");
        }
        repository.deleteById(id);
    }
}
