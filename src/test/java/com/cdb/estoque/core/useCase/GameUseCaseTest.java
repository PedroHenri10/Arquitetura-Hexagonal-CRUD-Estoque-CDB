package com.cdb.estoque.core.useCase;

import com.cdb.estoque.core.domain.model.Game;
import com.cdb.estoque.exception.ResourceNotFoundException;
import com.cdb.estoque.factory.GameFactoryBot;
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
    void  salvarGame_deveSalvarComSucesso(){
        Game game = GameFactoryBot.createDefaultGame();

        when(repo.save(game)).thenReturn(game);

        Game resultado = gameUseCase.save(game);

        assertEquals(game, resultado);

        verify(repo, times(1)).save(game);
    }

    @Test
    void buscarGamePorId_retornaGameCorreto(){
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);

        when(repo.findById(id)).thenReturn(Optional.of(game));

        Game resultado = gameUseCase.findById(id).get();

        assertEquals(game, resultado);

        verify(repo, times(1)).findById(id);

    }

    @Test
    void buscarTodosGame_retornarTodos(){
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);

        when(repo.findAll()).thenReturn(List.of(game));

        List<Game> resultado = gameUseCase.findAll();

        assertEquals(1, resultado.size());
        assertEquals(game, resultado.get(0));

        verify(repo, times(1)).findAll();
    }

    @Test
    void atualizarGame_deveAtualizarCorretamente(){
        Long id = 1L;

        Game gameExiste = GameFactoryBot.createGameWithId(id);
        Game gameAtualizado = GameFactoryBot.createCustomGame(id, "Teste", "PC", "Drama", 180.0, 10);

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
    void atualizarGame_deveLancarExcecaoQuandoNaoExiste() {
        Long id = 99L;
        Game gameAtualizado = GameFactoryBot.createCustomGame(id, "Teste", "PC", "Drama", 180.0, 10);

        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gameUseCase.update(id, gameAtualizado))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Game not found");

        verify(repo).findById(id);
        verify(repo, never()).save(any(Game.class));
    }

    @Test
    void  aumentarEstoque_deveIncrementarValorCorretamente() {
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);

        when(repo.findById(id)).thenReturn(Optional.of(game));
        when(repo.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Game result = gameUseCase.increaseStock(id, 5);

        assertNotNull(result);

        assertEquals(15, result.getStock());

        verify(repo).findById(id);
        verify(repo).save(game);


    }

    @Test
    void aumentarEstoque_deveLancarExcecaoQuandoValorZero() {
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);

        when(repo.findById(id)).thenReturn(Optional.of(game));

        assertThatThrownBy(() -> gameUseCase.increaseStock(id, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be positive");

        verify(repo).findById(id);
        verify(repo, never()).save(any(Game.class));
    }

    @Test
    void aumentarEstoque_deveLancarExcecaoQuandoValorNegativo() {
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);

        when(repo.findById(id)).thenReturn(Optional.of(game));

        assertThatThrownBy(() -> gameUseCase.increaseStock(id, -5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be positive");


        verify(repo).findById(id);
        verify(repo, never()).save(any(Game.class));
    }

    @Test
    void aumentarEstoque_deveLancarExcecaoQuandoIdNaoExiste() {
        Long id = 99L;

        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gameUseCase.increaseStock(id, 5))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Game not found");

        verify(repo).findById(id);
        verify(repo, never()).save(any(Game.class));
    }

    @Test
    void diminuirEstoque_deveDiminuirCorretamente(){
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);

        when(repo.findById(id)).thenReturn(Optional.of(game));

        gameUseCase.decreaseStock(id, 3);

        assertEquals(7, game.getStock());

        verify(repo).save(game);

    }


    @Test
    void diminuirEstoque_deveLancarExcecaoQuandoQuantidadeForZero() {
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);

        when(repo.findById(id)).thenReturn(Optional.of(game));

        assertThatThrownBy(() -> gameUseCase.decreaseStock(id, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be positive");

        verify(repo).findById(id);
        verify(repo, never()).save(any(Game.class));
    }

    @Test
    void diminuirEstoque_deveLancarExcecaoQuandoQuantidadeNegativa() {
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);

        when(repo.findById(id)).thenReturn(Optional.of(game));

        assertThatThrownBy(() -> gameUseCase.decreaseStock(id, -5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be positive");

        verify(repo).findById(id);
        verify(repo, never()).save(any(Game.class));
    }

    @Test
    void diminuirEstoque_deveLancarExcecaoQuandoIdNaoExiste() {
        Long id = 99L;

        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gameUseCase.decreaseStock(id, 5))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Game not found");

        verify(repo).findById(id);
        verify(repo, never()).save(any(Game.class));
    }

    @Test
    void diminuirEstoque_deveLancarExcecaoQuandoQuantidadeMaiorQueEstoque() {
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);

        when(repo.findById(id)).thenReturn(Optional.of(game));

        assertThatThrownBy(() -> gameUseCase.decreaseStock(id, 20))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Not enough stock for the game");

        verify(repo).findById(id);
        verify(repo, never()).save(any(Game.class));
    }

    @Test
    void buscarPorTitulo_retornaListaDeJogosQuandoExistir(){
        String titulo ="Jogo";
        Game game = GameFactoryBot.createCustomGame(1L, "Jogo", "PC", "Ação", 150.0, 10);

        when(repo.findByTitleGameContainingIgnoreCase(titulo)).thenReturn(List.of(game));


        List<Game>  resultado = gameUseCase.findByTitleGameContainingIgnoreCase(titulo);

        assertEquals(1, resultado.size());
        assertEquals("Jogo", resultado.get(0).getTitleGame());
        verify(repo).findByTitleGameContainingIgnoreCase(titulo);

    }

    @Test
    void buscarPorTitulo_lancaExcecaoQuandoNaoEncontrado() {
        String titulo ="Inexistente";

        when(repo.findByTitleGameContainingIgnoreCase(titulo))
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> gameUseCase.findByTitleGameContainingIgnoreCase(titulo))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Game not found");


        verify(repo).findByTitleGameContainingIgnoreCase(titulo);
    }

    @Test
    void buscarPorGenero_retornaListaQuandoEncontrado() {
        String genero ="Drama";
        Game game = GameFactoryBot.createCustomGame(2L, "Jogo Teste", "PC", "Drama", 150.0, 10);

        when(repo.findByGenreContainingIgnoreCase(genero)).thenReturn(List.of(game));

        List<Game>  resultado = gameUseCase.findByGenreContainingIgnoreCase(genero);

        assertEquals(1, resultado.size());
        assertEquals("Drama", resultado.get(0).getGenre());
        verify(repo).findByGenreContainingIgnoreCase(genero);

    }

    @Test
    void buscarPorGenero_lancaExcecaoQuandoNaoEncontrado() {
        String genero ="Drama";

        when(repo.findByGenreContainingIgnoreCase(genero))
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> gameUseCase.findByGenreContainingIgnoreCase(genero))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Game not found");


        verify(repo).findByGenreContainingIgnoreCase(genero);
    }

    @Test
   void buscarPorPlataforma_retornaListaQuandoEncontrada(){
        String plataforma = "PC";
        Game game = GameFactoryBot.createCustomGame(2L, "Jogo Teste", "PC", "Drama", 150.0, 10);

        when(repo.findByPlataformContainingIgnoreCase(plataforma)).thenReturn(List.of(game));

        List<Game>  resultado = gameUseCase.findByPlataformContainingIgnoreCase(plataforma);

        assertEquals(1, resultado.size());
        assertEquals("PC", resultado.get(0).getPlataform());
        verify(repo).findByPlataformContainingIgnoreCase(plataforma);

    }

    @Test
    void buscarPorPlataformaInexistente_lancaExcecao() {
        String plataforma = "PC";

        when(repo.findByPlataformContainingIgnoreCase(plataforma))
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> gameUseCase.findByPlataformContainingIgnoreCase(plataforma))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Game not found");

        verify(repo).findByPlataformContainingIgnoreCase(plataforma);
    }

    @Test
    void deletarJogo_sucessoQuandoExistente() {
        Long id = 1L;

        when(repo.existsById(id)).thenReturn(true);
        doNothing().when(repo).deleteById(id);
        gameUseCase.deleteById(id);
        verify(repo).deleteById(id);

    }

    @Test
    void deletarJogo_lancaExcecaoQuandoNaoEncontrado() {
        Long id = 1L;

        when(repo.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> gameUseCase.deleteById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Game not found");

        verify(repo).existsById(id);
        verify(repo, never()).deleteById(anyLong());
    }

    @Test
    void buscarPorId_lancaExcecaoQuandoNaoEncontrado() {
        Long id = 99L;
        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gameUseCase.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game not found")))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Game not found");

        verify(repo).findById(id);
    }

    @Test
    void salvarGame_comPrecoZero_deveSalvar() {
        Game jogoGratis = GameFactoryBot.createGameWithZeroPrice();
        when(repo.save(jogoGratis)).thenReturn(jogoGratis);

        Game resultado = gameUseCase.save(jogoGratis);

        assertEquals(0.0, resultado.getPrice(), 0.001);
        verify(repo).save(jogoGratis);
    }

    @Test
    void aumentarEstoque_quandoJogoSemEstoque_deveIncrementar() {
        Game semEstoque = GameFactoryBot.createGameOutOfStock();
        when(repo.findById(1L)).thenReturn(Optional.of(semEstoque));
        when(repo.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Game resultado = gameUseCase.increaseStock(1L, 5);

        assertEquals(5, resultado.getStock());
        verify(repo).save(semEstoque);
    }

    @Test
    void diminuirEstoque_quandoJogoComEstoqueNegativo_deveLancarExcecao() {
        Game estoqueNegativo = GameFactoryBot.createGameWithNegativeStock();
        when(repo.findById(1L)).thenReturn(Optional.of(estoqueNegativo));

        assertThatThrownBy(() -> gameUseCase.decreaseStock(1L, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Not enough stock for the game");

        verify(repo).findById(1L);
    }

    @Test
    void salvarJogoAleatorio_deveSalvar() {
        Game aleatorio = GameFactoryBot.createRandomGame();
        when(repo.save(aleatorio)).thenReturn(aleatorio);

        Game resultado = gameUseCase.save(aleatorio);

        assertNotNull(resultado.getId());
        assertTrue(resultado.getPrice() >= 0);
        assertTrue(resultado.getStock() >= 0);
        verify(repo).save(aleatorio);
    }

    @Test
    void buscarTodosJogos_deveRetornarListaComVariosJogos() {
        List<Game> listaJogos = GameFactoryBot.createListOfGames();
        when(repo.findAll()).thenReturn(listaJogos);

        List<Game> resultado = gameUseCase.findAll();

        assertEquals(3, resultado.size());
        assertEquals("Jogo 1", resultado.get(0).getTitleGame());
        assertEquals("Jogo 2", resultado.get(1).getTitleGame());
        assertEquals("Jogo 3", resultado.get(2).getTitleGame());
        verify(repo).findAll();
    }

}
