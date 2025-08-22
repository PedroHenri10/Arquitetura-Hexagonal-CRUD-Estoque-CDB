package com.cdb.estoque.service;

import com.cdb.estoque.dto.GameDTO;
import com.cdb.estoque.entity.Game;
import com.cdb.estoque.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

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

    
}
