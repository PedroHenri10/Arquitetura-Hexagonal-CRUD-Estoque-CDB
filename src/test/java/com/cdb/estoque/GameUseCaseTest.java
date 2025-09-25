package com.cdb.estoque;

import com.cdb.estoque.core.domain.model.Game;
import com.cdb.estoque.core.useCase.GameUseCase;
import com.cdb.estoque.port.output.GameRepositoryPort;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



public class GameUseCaseTest {

    GameRepositoryPort repo = mock(GameRepositoryPort.class);

    GameUseCase gameUseCase = new GameUseCase(repo);

    @Test
    void saveGame_ok(){
        Game game = new Game(null, "Jogo Teste", "PC", "Ação", 150.0, 10);

        when(repo.save(game)).thenReturn(game);

        Game resultado = gameUseCase.save(game);

        assertEquals(game, resultado);

        verify(repo, times(1)).save(game);
    }

    @Test
    void buscarGamePorId_retornaGameCorreto(){
        Long id = 1L;
        Game game = new Game(id, "Jogo Teste", "PC", "Ação", 150.0, 10);

        when(repo.findById(id)).thenReturn(Optional.of(game));

        Game resultado = gameUseCase.findById(id).get();

        assertEquals(game, resultado);

        verify(repo, times(1)).findById(id);

    }

    @Test
    void buscarTodosGame_retornarTudo(){
        Long id = 1L;
        Game game = new Game(id, "Jogo Teste", "PC", "Ação", 150.0, 10);

        when(repo.findAll()).thenReturn(List.of(game));

        List<Game> resultado = gameUseCase.findAll();

        assertEquals(1, resultado.size());
        assertEquals(game, resultado.get(0));

        verify(repo, times(1)).findAll();
    }

    @Test
    void atualizarGame_atualizadoOK(){
        Long id = 1L;

        Game gameExiste = new Game(id, "Jogo Teste", "PC", "Ação", 150.0, 10);
        Game gameAtualizado = new Game(id, "Teste", "PC", "Drama", 180.0, 10);

        when(repo.findById(id)).thenReturn(Optional.of(gameExiste));

        when(repo.save(gameAtualizado)).thenReturn(gameAtualizado);

        Game result = gameUseCase.update(id, gameAtualizado);

        assertNotNull(result);

        assertEquals("Teste", result.getTitleGame());
        assertEquals("Drama", result.getGenre());
        assertEquals("PC", result.getPlataform());
        assertEquals(180.00, result.getPrice(), 0.001);
        assertEquals(10, result.getStock());

        verify(repo).findById(id);
        verify(repo).save(gameAtualizado);

    }

    @Test
    void  increaseStock_incrementaValorOK() {
        Long id = 1L;
        Game game = new Game(id, "Jogo Teste", "PC", "Ação", 150.0, 10);

        when(repo.findById(id)).thenReturn(Optional.of(game));
        when(repo.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Game result = gameUseCase.increaseStock(id, 5);

        assertNotNull(result);

        assertEquals(15, result.getStock());

        verify(repo).findById(id);
        verify(repo).save(game);


    }

    @Test
    void  increaseStock_quantidadeInvalidaLancaExeception() {
        Long id = 1L;
        Game game = new Game(id, "Jogo Teste", "PC", "Ação", 150.0, 10);

        when(repo.findById(id)).thenReturn(Optional.of(game));

        assertThrows(IllegalArgumentException.class, () -> {
            gameUseCase.increaseStock(id, 0);
        });

        verify(repo).findById(id);
        verify(repo, never()).save(any(Game.class));

    }

}
