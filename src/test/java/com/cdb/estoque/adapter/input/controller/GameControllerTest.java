package com.cdb.estoque.adapter.input.controller;

import com.cdb.estoque.adapter.input.mapper.GameRestMapper;
import com.cdb.estoque.adapter.input.request.GameRequest;
import com.cdb.estoque.adapter.input.response.GameResponse;
import com.cdb.estoque.core.domain.model.Game;
import com.cdb.estoque.exception.ResourceNotFoundException;
import com.cdb.estoque.port.input.GameInputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;



class GameControllerTest {

    @Mock
    private GameInputPort gameInputPort;

    @Mock
    private GameRestMapper mapper;

    @InjectMocks
    private GameController gameController;

    private Game game;
    private GameRequest gameRequest;
    private GameResponse gameResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        game = new Game(1L, "Zelda", "Switch", "Aventura", 250.0, 5);
        gameRequest = new GameRequest("Zelda", "Switch", "Aventura", 250.0, 5);
        gameResponse = new GameResponse(1L, "Zelda", "Switch", "Aventura", 250.0, 5);
    }

    @Test
    void deveListarTodosOsGames() {
        when(gameInputPort.findAll()).thenReturn(List.of(game));
        when(mapper.toResponse(game)).thenReturn(gameResponse);

        ResponseEntity<List<GameResponse>> response = gameController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void deveBuscarGamePorId() {
        when(gameInputPort.findById(1L)).thenReturn(Optional.of(game));
        when(mapper.toResponse(game)).thenReturn(gameResponse);

        ResponseEntity<GameResponse> response = gameController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gameResponse, response.getBody());
    }

    @Test
    void deveRetornar404QuandoNaoAcharGamePorId() {
        when(gameInputPort.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> gameController.findById(1L));
    }

    @Test
    void deveCriarGame() {
        when(mapper.toDomain(gameRequest)).thenReturn(game);
        when(gameInputPort.save(game)).thenReturn(game);
        when(mapper.toResponse(game)).thenReturn(gameResponse);

        ResponseEntity<GameResponse> response = gameController.createGame(gameRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(gameResponse, response.getBody());
        verify(gameInputPort, times(1)).save(game);
    }

    @Test
    void deveAtualizarGame() {
        when(mapper.toDomain(gameRequest)).thenReturn(game);
        when(gameInputPort.update(1L, game)).thenReturn(game);
        when(mapper.toResponse(game)).thenReturn(gameResponse);

        ResponseEntity<GameResponse> response = gameController.update(1L, gameRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gameResponse, response.getBody());
    }

    @Test
    void deveDeletarGame() {
        ResponseEntity<Void> response = gameController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(gameInputPort, times(1)).deleteById(1L);
    }

    @Test
    void deveAumentarEstoque() {
        Long id = 1L;
        int quantidade = 5;

        when(gameInputPort.increaseStock(id, quantidade)).thenReturn(game);
        when(mapper.toResponse(game)).thenReturn(gameResponse);

        ResponseEntity<GameResponse> response = gameController.increaseStock(id, quantidade);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gameResponse, response.getBody());
        verify(gameInputPort, times(1)).increaseStock(id, quantidade);
    }

    @Test
    void deveDiminuirEstoque() {
        Long id = 1L;
        int quantidade = 3;

        when(gameInputPort.decreaseStock(id, quantidade)).thenReturn(game);
        when(mapper.toResponse(game)).thenReturn(gameResponse);

        ResponseEntity<GameResponse> response = gameController.decreaseStock(id, quantidade);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gameResponse, response.getBody());
        verify(gameInputPort, times(1)).decreaseStock(id, quantidade);
    }

    @Test
    void deveBuscarPorTituloIgnoreCase() {
        String title = "teste";

        when(gameInputPort.findByTitleGameContainingIgnoreCase(title)).thenReturn(List.of(game));
        when(mapper.toResponse(game)).thenReturn(gameResponse);

        ResponseEntity<List<GameResponse>> response = gameController.findByTitleGameContainingIgnoreCase(title);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(gameResponse, response.getBody().get(0));
    }

    @Test
    void deveBuscarPorGeneroIgnoreCase() {
        String genre = "acao";

        when(gameInputPort.findByGenreContainingIgnoreCase(genre)).thenReturn(List.of(game));
        when(mapper.toResponse(game)).thenReturn(gameResponse);

        ResponseEntity<List<GameResponse>> response = gameController.findByGenreContainingIgnoreCase(genre);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(gameResponse, response.getBody().get(0));
    }

    @Test
    void deveBuscarPorPlataformaIgnoreCase() {
        String plataform = "PC";

        when(gameInputPort.findByPlataformContainingIgnoreCase(plataform)).thenReturn(List.of(game));
        when(mapper.toResponse(game)).thenReturn(gameResponse);

        ResponseEntity<List<GameResponse>> response = gameController.findByPlataformContainingIgnoreCase(plataform);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(gameResponse, response.getBody().get(0));
    }

}
