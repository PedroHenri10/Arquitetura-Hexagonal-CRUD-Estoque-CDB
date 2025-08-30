package com.cdb.estoque.adapter.input.controller;

import com.cdb.estoque.adapter.input.mapper.GameRestMapper;
import com.cdb.estoque.adapter.input.request.GameRequest;
import com.cdb.estoque.adapter.input.response.GameResponse;
import com.cdb.estoque.core.domain.model.Game;
import com.cdb.estoque.exception.ResourceNotFoundException;
import com.cdb.estoque.port.input.GameInputPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameInputPort gameInputPort;
    private final GameRestMapper mapper;

    @GetMapping
    public ResponseEntity<List<GameResponse>> findAll(){
        List<GameResponse> allGames = gameInputPort.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(allGames);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> findById(@PathVariable Long id) {
        Game game = gameInputPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("GAME NOT FOUND"));
        return ResponseEntity.ok(mapper.toResponse(game));
    }

    @PostMapping
    public ResponseEntity<GameResponse> createGame(@Valid @RequestBody GameRequest request) {
       Game toSave = mapper.toDomain(request);
       Game saved = gameInputPort.save(toSave);
        return ResponseEntity.created(URI.create("/games/" + saved.getId())).body(mapper.toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameResponse> updateGame(@PathVariable Long id, @Valid @RequestBody GameRequest game) {
        var gameToUpdate = mapper.toDomain(request);
        var updatedGame = gameInputPort.update(id, gameToUpdate);
        return ResponseEntity.ok(mapper.toResponse(updated game));
    }

    @PatchMapping("/{id}/increase-stock")
    public ResponseEntity<GameResponse> increaseStock(@PathVariable Long id, @RequestParam int quantity) {
        var updatedGame gameInputPort.increaseStock(id, quantity);
        return ResponseEntity.ok(mapper.toResponse(updatedGame));
    }

    @PatchMapping("/{id}/decrease-stock")
    public ResponseEntity<GameResponse> decreaseStock(@PathVariable Long id, @RequestParam int quantity) {
        var updatedGame gameInputPort.decreaseStock(id, quantity);
        return ResponseEntity.ok(mapper.toResponse(updatedGame));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gameInputPort.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
