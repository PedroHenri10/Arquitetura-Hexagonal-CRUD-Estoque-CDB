package com.cdb.estoque.adapter.input.controller;

import com.cdb.estoque.adapter.input.request.GameRequest;
import com.cdb.estoque.core.userCase.GameUserCase;
import com.cdb.estoque.port.input.GameInputPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameInputPort;
    //private final GameRestMapper mapper;

    @GetMapping
    public ResponseEntity<List<GameRequest>> getAllGames(){
        List<GameResponse> allGames = gameInputPort.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(allGames);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameRequest> getGameById(@PathVariable Long id) {
        return gameService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GameRequest> createGame(@RequestBody GameRequest dto) {
        GameRequest savedDto = gameService.save(dto);
        return ResponseEntity.status(201).body(savedDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameRequest> update(@PathVariable Long id, @Valid @RequestBody GameRequest dto) {
        return ResponseEntity.ok(gameService.update(id, dto));
    }

    @PostMapping("/{id}/increase-stock")
    public GameRequest increaseStock(@PathVariable Long id, @RequestParam int quantity) {
        return gameService.increaseStock(id, quantity);
    }

    @PostMapping("/{id}/decrease-stock")
    public GameRequest decreaseStock(@PathVariable Long id, @RequestParam int quantity) {
        return gameService.decreaseStock(id, quantity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
