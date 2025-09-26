package com.cdb.estoque.application;

import com.cdb.estoque.core.domain.model.Game;
import com.cdb.estoque.core.useCase.GameUseCase;
import com.cdb.estoque.exception.ResourceNotFoundException;
import com.cdb.estoque.port.output.GameRepositoryPort;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class GameUseCaseTest {

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
    void increaseStock_lancaExcecaoQuandoZero() {
        Long id = 1L;
        Game game = new Game(id, "Jogo Teste", "PC", "Ação", 150.0, 10);

        when(repo.findById(id)).thenReturn(Optional.of(game));

        assertThatThrownBy(() -> gameUseCase.increaseStock(id, 0))
                .isInstanceOf(IllegalArgumentException.class);

        verify(repo).findById(id);
        verify(repo, never()).save(any(Game.class));
    }

    @Test
    void increaseStock_lancaExcecaoQuandoNegativo() {
        Long id = 1L;
        Game game = new Game(id, "Jogo Teste", "PC", "Ação", 150.0, 10);

        when(repo.findById(id)).thenReturn(Optional.of(game));

        assertThatThrownBy(() -> gameUseCase.increaseStock(id, -5))
                .isInstanceOf(IllegalArgumentException.class);

        verify(repo).findById(id);
        verify(repo, never()).save(any(Game.class));
    }

    @Test
    void decreaseStock_decrementaEstoqueCorretamente(){
        Long id = 1L;
        Game game = new Game(id, "Jogo Teste", "PC", "Ação", 150.0, 10);

        when(repo.findById(id)).thenReturn(Optional.of(game));

        gameUseCase.decreaseStock(id, 3);

        assertEquals(7, game.getStock());

        verify(repo).save(game);

    }


    @Test
    void decreaseStock_lancaExcecaoQuandoZero() {
        Long id = 1L;
        Game game = new Game(id, "Jogo Teste", "PC", "Ação", 150.0, 10);

        when(repo.findById(id)).thenReturn(Optional.of(game));

        assertThatThrownBy(() -> gameUseCase.decreaseStock(id, 0))
                .isInstanceOf(IllegalArgumentException.class);

        verify(repo).findById(id);
        verify(repo, never()).save(any(Game.class));
    }

    @Test
    void decreaseStock_lancaExcecaoQuandoNegativo() {
        Long id = 1L;
        Game game = new Game(id, "Jogo Teste", "PC", "Ação", 150.0, 10);

        when(repo.findById(id)).thenReturn(Optional.of(game));

        assertThatThrownBy(() -> gameUseCase.decreaseStock(id, -5))
                .isInstanceOf(IllegalArgumentException.class);

        verify(repo).findById(id);
        verify(repo, never()).save(any(Game.class));
    }

    @Test
    void findByTitleGameContainingIgnoreCase_retornaListaQuandoEncontrado(){
        String titulo ="Jogo";
        Game game = new Game(1L, "Jogo", "PC", "Ação", 150.0, 10);

        when(repo.findByTitleGameContainingIgnoreCase(titulo)).thenReturn(List.of(game));


        List<Game>  resultado = gameUseCase.findByTitleGameContainingIgnoreCase(titulo);

        assertEquals(1, resultado.size());
        assertEquals("Jogo", resultado.get(0).getTitleGame());
        verify(repo).findByTitleGameContainingIgnoreCase(titulo);

    }

    @Test
    void findByTitleGameContainingIgnoreCase_exception() {
        String titulo ="Inexistente";

        when(repo.findByTitleGameContainingIgnoreCase(titulo))
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> gameUseCase.findByTitleGameContainingIgnoreCase(titulo))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repo).findByTitleGameContainingIgnoreCase(titulo);
    }

    @Test
    void  findByGenreContainingIgnoreCase_retornaListaQuandoEncontrado(){
        String genero ="Drama";
        Game game = new Game(2L, "Jogo Teste", "PC", "Drama", 150.0, 10);

        when(repo.findByGenreContainingIgnoreCase(genero)).thenReturn(List.of(game));

        List<Game>  resultado = gameUseCase.findByGenreContainingIgnoreCase(genero);

        assertEquals(1, resultado.size());
        assertEquals("Drama", resultado.get(0).getGenre());
        verify(repo).findByGenreContainingIgnoreCase(genero);

    }

    @Test
    void findByGenreContainingIgnoreCase_exception() {
        String genero ="Drama";

        when(repo.findByGenreContainingIgnoreCase(genero))
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> gameUseCase.findByGenreContainingIgnoreCase(genero))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repo).findByGenreContainingIgnoreCase(genero);
    }

    @Test
    void findByPlataformContainingIgnoreCase_retornaListaQuandoEncontrado(){
        String plataforma = "PC";
        Game game = new Game(2L, "Jogo Teste", "PC", "Drama", 150.0, 10);;

        when(repo.findByPlataformContainingIgnoreCase(plataforma)).thenReturn(List.of(game));

        List<Game>  resultado = gameUseCase.findByPlataformContainingIgnoreCase(plataforma);

        assertEquals(1, resultado.size());
        assertEquals("PC", resultado.get(0).getPlataform());
        verify(repo).findByPlataformContainingIgnoreCase(plataforma);

    }

    @Test
    void findByPlataformContainingIgnoreCase_exception() {
        String plataforma = "PC";

        when(repo.findByPlataformContainingIgnoreCase(plataforma))
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> gameUseCase.findByPlataformContainingIgnoreCase(plataforma))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repo).findByPlataformContainingIgnoreCase(plataforma);
    }

    @Test
    void deleteGame_ok(){
        Long id = 1L;

        when(repo.existsById(id)).thenReturn(true);
        doNothing().when(repo).deleteById(id);
        gameUseCase.deleteById(id);
        verify(repo).deleteById(id);

    }

    @Test
    void deleteGame_exception(){
        Long id = 1L;

        when(repo.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> gameUseCase.deleteById(id))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repo).existsById(id);
        verify(repo, never()).deleteById(anyLong());
    }

}
