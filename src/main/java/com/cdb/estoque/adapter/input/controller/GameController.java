package com.cdb.estoque.adapter.input.controller;

import com.cdb.estoque.adapter.input.request.GameRequest;
import com.cdb.estoque.core.userCase.GameUserCase;
import com.cdb.estoque.port.input.GameInputPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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
        return gameInputPort.findById(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok);

        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GameRequest> createGame(@Valid @RequestBody GameRequest request) {
        var gameDomain = mapper.toDomain(request);
        var savedGame = gameInputPort.save(gameDomain);
        return ResponseEntity.status(201).body(mapper.toResponse(savedGame));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameRequest> updateGame(@PathVariable Long id, @Valid @RequestBody GameRequest game) {
        var gameToUpdate = mapper.toDomain(request);
        var updatedGame = gameInputPort.update(id, gameToUpdate);
        return ResponseEntity.ok(mapper.toResponse(updated game));
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
