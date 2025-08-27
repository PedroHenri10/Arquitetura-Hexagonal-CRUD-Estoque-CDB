package com.cdb.estoque.core.userCase;

import com.cdb.estoque.adapter.input.request.GameRequest;
import com.cdb.estoque.adapter.output.entity.Game;
import com.cdb.estoque.exception.ResourceNotFoundException;
import com.cdb.estoque.adapter.output.repository.GameRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameUserCase {

    @Autowired
    private GameRepository repository;

    private GameRequest convertToDTO(Game game){
        return new GameRequest(
                game.getId(),
                game.getTitleGame(),
                game.getPlataform(),
                game.getGenre(),
                game.getPrice(),
                game.getStock()
        );
    }

    private Game convertToEntity(GameRequest dto){
        Game game = new Game();
        game.setId(dto.getId());
        game.setTitleGame(dto.getTitleGame());
        game.setPlataform(dto.getPlataform());
        game.setGenre(dto.getGenre());
        game.setPrice(dto.getPrice());
        game.setStock(dto.getStock());
        return game;
    }

    public List<GameRequest> listAll(){
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<GameRequest> findById(Long id){
        return repository.findById(id).map(this::convertToDTO);
    }

    public GameRequest save(GameRequest dto){
        Game game = convertToEntity(dto);
        return convertToDTO(repository.save(game));
    }

    public GameRequest update(Long id, GameRequest dto){
        Game game = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game not found"));

        game.setTitleGame(dto.getTitleGame());
        game.setPlataform(dto.getPlataform());
        game.setGenre(dto.getGenre());
        game.setStock(dto.getStock());
        game.setPrice(dto.getPrice());

        return convertToDTO(repository.save(game));
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
