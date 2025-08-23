package com.cdb.estoque.controller;

import com.cdb.estoque.dto.GameDTO;
import com.cdb.estoque.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public List<GameDTO> getAllGames(){
        return gameService.listAll();
    }

    @PostMapping
    public ResponseEntity<GameDTO> createGame(@RequestBody GameDTO dto) {
        return ResponseEntity.ok(gameService.save(dto));
    }
}

