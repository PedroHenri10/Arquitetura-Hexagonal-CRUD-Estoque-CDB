package com.cdb.estoque.service;

import com.cdb.estoque.dto.GameDTO;
import com.cdb.estoque.entity.Game;
import com.cdb.estoque.exception.ResourceNotFoundException;
import com.cdb.estoque.repository.GameRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private GameRepository repository;

    private GameDTO convertToDTO(Game game){
        return new GameDTO(
                game.getId(),
                game.getTitleGame(),
                game.getPlataform(),
                game.getPrice(),
                game.getStock()
        );
    }

    private Game convertToEntity(GameDTO dto){
        Game game = new Game();
        game.setId(dto.getId());
        game.setTitleGame(dto.getTitleGame());
        game.setPlataform(dto.getPlataform());
        game.setPrice(dto.getPrice());
        game.setStock(dto.getStock());
        return game;
    }

    public List<GameDTO> listAll(){
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public GameDTO findById(Long id){
        return repository.findById(id).map(this::convertToDTO).orElseThrow(()-> new RuntimeException("Game not found"));
    }

    public GameDTO save(GameDTO dto){
        Game game = convertToEntity(dto);
        return convertToDTO(repository.save(game));
    }

    public GameDTO update(Long id, GameDTO dto){
        Game game = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game not found"));

        game.setTitleGame(dto.getTitleGame());
        game.setPlataform(dto.getPlataform());
        game.setStock(dto.getStock());
        game.setPrice(dto.getPrice());

        return convertToDTO(repository.save(game));
    }

    @Transactional
    public GameDTO increaseStock(Long id, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive.");
        return repository.findById(id)
                .map(game -> {
                    game.setStock(game.getStock() + quantity);
                    return convertToDTO(repository.save(game));
                })
                .orElseThrow(() -> new RuntimeException("Game not found"));
    }

    @Transactional
    public GameDTO decreaseStock(Long id, int quantity) {
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
