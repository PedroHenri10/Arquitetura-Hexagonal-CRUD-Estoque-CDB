package com.cdb.estoque.core.useCase;

import com.cdb.estoque.core.domain.model.Game;
import com.cdb.estoque.core.domain.strategy.IncreaseStockOperation;
import com.cdb.estoque.core.domain.strategy.DecreaseStockOperation;
import com.cdb.estoque.exception.ResourceNotFoundException;
import com.cdb.estoque.factory.GameFactoryBot;
import com.cdb.estoque.port.output.GameRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameUseCaseTest {

    @Mock
    GameRepositoryPort gameRepositoryPort;
    @Mock
    IncreaseStockOperation increaseStockOperation;
    @Mock
    DecreaseStockOperation decreaseStockOperation;

    @InjectMocks
    GameUseCase gameUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void  salvarGame_deveSalvarComSucesso(){
        Game game = GameFactoryBot.createDefaultGame();

        when(gameRepositoryPort.save(game)).thenReturn(game);

        Game resultado = gameUseCase.save(game);

        assertEquals(game, resultado);

        verify(gameRepositoryPort, times(1)).save(game);
    }

    @Test
    void buscarGamePorId_retornaGameCorreto(){
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);

        when(gameRepositoryPort.findById(id)).thenReturn(Optional.of(game));

        Game resultado = gameUseCase.findById(id).get();

        assertEquals(game, resultado);

        verify(gameRepositoryPort, times(1)).findById(id);

    }

    @Test
    void buscarTodosGame_retornarTodos(){
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);

        when(gameRepositoryPort.findAll()).thenReturn(List.of(game));

        List<Game> resultado = gameUseCase.findAll();

        assertEquals(1, resultado.size());
        assertEquals(game, resultado.get(0));

        verify(gameRepositoryPort, times(1)).findAll();
    }

    @Test
    void atualizarGame_deveAtualizarCorretamente(){
        Long id = 1L;

        Game gameExiste = GameFactoryBot.createGameWithId(id);
        Game gameAtualizado = GameFactoryBot.createCustomGame(id, "Teste", "PC", "Drama", 180.0, 10);

        when(gameRepositoryPort.findById(id)).thenReturn(Optional.of(gameExiste));
        when(gameRepositoryPort.save(gameAtualizado)).thenReturn(gameAtualizado);

        Game result = gameUseCase.update(id, gameAtualizado);

        assertNotNull(result);

        assertEquals("Teste", result.getTitleGame());
        assertEquals("Drama", result.getGenre());
        assertEquals("PC", result.getPlataform());
        assertEquals(180.00, result.getPrice(), 0.001);
        assertEquals(10, result.getStock());

        verify(gameRepositoryPort).findById(id);
        verify(gameRepositoryPort).save(gameAtualizado);

    }

    @Test
    void atualizarGame_deveLancarExcecaoQuandoNaoExiste() {
        Long id = 99L;
        Game gameAtualizado = GameFactoryBot.createCustomGame(id, "Teste", "PC", "Drama", 180.0, 10);

        when(gameRepositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gameUseCase.update(id, gameAtualizado))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Game not found");

        verify(gameRepositoryPort).findById(id);
        verify(gameRepositoryPort, never()).save(any(Game.class));
    }

    @Test
    void aumentarEstoque_deveLancarExcecaoQuandoIdNaoExiste() {
        Long id = 99L;

        when(gameRepositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gameUseCase.increaseStock(id, 5))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Game not found");

        verify(gameRepositoryPort).findById(id);
        verify(gameRepositoryPort, never()).save(any(Game.class));
    }

    @Test
    void diminuirEstoque_deveLancarExcecaoQuandoIdNaoExiste() {
        Long id = 99L;

        when(gameRepositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gameUseCase.decreaseStock(id, 5))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Game not found");

        verify(gameRepositoryPort).findById(id);
        verify(gameRepositoryPort, never()).save(any(Game.class));
    }

    @Test
    void buscarPorTitulo_retornaListaDeJogosQuandoExistir(){
        String titulo ="Jogo";
        Game game = GameFactoryBot.createCustomGame(1L, "Jogo", "PC", "Ação", 150.0, 10);

        when(gameRepositoryPort.findByTitleGameContainingIgnoreCase(titulo)).thenReturn(List.of(game));


        List<Game>  resultado = gameUseCase.findByTitleGameContainingIgnoreCase(titulo);

        assertEquals(1, resultado.size());
        assertEquals("Jogo", resultado.get(0).getTitleGame());
        verify(gameRepositoryPort).findByTitleGameContainingIgnoreCase(titulo);

    }

    @Test
    void buscarPorGenero_retornaListaQuandoEncontrado() {
        String genero ="Drama";
        Game game = GameFactoryBot.createCustomGame(2L, "Jogo Teste", "PC", "Drama", 150.0, 10);

        when(gameRepositoryPort.findByGenreContainingIgnoreCase(genero)).thenReturn(List.of(game));

        List<Game>  resultado = gameUseCase.findByGenreContainingIgnoreCase(genero);

        assertEquals(1, resultado.size());
        assertEquals("Drama", resultado.get(0).getGenre());
        verify(gameRepositoryPort).findByGenreContainingIgnoreCase(genero);

    }
//
    @Test
    void buscarPorGenero_lancaExcecaoQuandoNaoEncontrado() {
        String genero ="Drama";

        when(gameRepositoryPort.findByGenreContainingIgnoreCase(genero))
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> gameUseCase.findByGenreContainingIgnoreCase(genero))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Game not found");


        verify(gameRepositoryPort).findByGenreContainingIgnoreCase(genero);
    }

    @Test
   void buscarPorPlataforma_retornaListaQuandoEncontrada(){
        String plataforma = "PC";
        Game game = GameFactoryBot.createCustomGame(2L, "Jogo Teste", "PC", "Drama", 150.0, 10);

        when(gameRepositoryPort.findByPlataformContainingIgnoreCase(plataforma)).thenReturn(List.of(game));

        List<Game>  resultado = gameUseCase.findByPlataformContainingIgnoreCase(plataforma);

        assertEquals(1, resultado.size());
        assertEquals("PC", resultado.get(0).getPlataform());
        verify(gameRepositoryPort).findByPlataformContainingIgnoreCase(plataforma);

    }

    @Test
    void deletarJogo_sucessoQuandoExistente() {
        Long id = 1L;

        when(gameRepositoryPort.existsById(id)).thenReturn(true);
        doNothing().when(gameRepositoryPort).deleteById(id);
        gameUseCase.deleteById(id);
        verify(gameRepositoryPort).deleteById(id);

    }

    @Test
    void buscarPorId_lancaExcecaoQuandoNaoEncontrado() {
        Long id = 99L;
        when(gameRepositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gameUseCase.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game not found")))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Game not found");

        verify(gameRepositoryPort).findById(id);
    }

    @Test
    void salvarGame_comPrecoZero_deveSalvar() {
        Game jogoGratis = GameFactoryBot.createGameWithZeroPrice();
        when(gameRepositoryPort.save(jogoGratis)).thenReturn(jogoGratis);

        Game resultado = gameUseCase.save(jogoGratis);

        assertEquals(0.0, resultado.getPrice(), 0.001);
        verify(gameRepositoryPort).save(jogoGratis);
    }

    @Test
    void salvarJogoAleatorio_deveSalvar() {
        Game aleatorio = GameFactoryBot.createRandomGame();
        when(gameRepositoryPort.save(aleatorio)).thenReturn(aleatorio);

        Game resultado = gameUseCase.save(aleatorio);

        assertNotNull(resultado.getId());
        assertTrue(resultado.getPrice() >= 0);
        assertTrue(resultado.getStock() >= 0);
        verify(gameRepositoryPort).save(aleatorio);
    }

    @Test
    void buscarTodosJogos_deveRetornarListaComVariosJogos() {
        List<Game> listaJogos = GameFactoryBot.createListOfGames();
        when(gameRepositoryPort.findAll()).thenReturn(listaJogos);

        List<Game> resultado = gameUseCase.findAll();

        assertEquals(3, resultado.size());
        assertEquals("Jogo 1", resultado.get(0).getTitleGame());
        assertEquals("Jogo 2", resultado.get(1).getTitleGame());
        assertEquals("Jogo 3", resultado.get(2).getTitleGame());
        verify(gameRepositoryPort).findAll();
    }
    //

    @Test
    void aumentarEstoque_deveIncrementarValorCorretamente() {
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);

        when(gameRepositoryPort.findById(id)).thenReturn(Optional.of(game));
        when(gameRepositoryPort.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

        doAnswer(invocation -> {
            Game g = invocation.getArgument(0);
            int q = invocation.getArgument(1);
            g.setStock(g.getStock() + q);
            return null;
        }).when(increaseStockOperation).execute(any(Game.class), eq(5));

        Game result = gameUseCase.increaseStock(id, 5);

        assertNotNull(result);
        assertEquals(15, result.getStock());

        verify(gameRepositoryPort).findById(id);
        verify(increaseStockOperation).execute(game, 5);
        verify(gameRepositoryPort).save(game);
    }

    @Test
    void aumentarEstoque_deveLancarExcecaoQuandoValorZero() {
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);

        when(gameRepositoryPort.findById(id)).thenReturn(Optional.of(game));

        doThrow(new IllegalArgumentException("Quantity must be positive"))
                .when(increaseStockOperation).execute(any(Game.class), eq(0));

        assertThatThrownBy(() -> gameUseCase.increaseStock(id, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be positive");

        verify(gameRepositoryPort).findById(id);
        verify(increaseStockOperation).execute(game, 0);
        verify(gameRepositoryPort, never()).save(any(Game.class));
    }

    @Test
    void aumentarEstoque_deveLancarExcecaoQuandoValorNegativo() {
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);

        when(gameRepositoryPort.findById(id)).thenReturn(Optional.of(game));

        doThrow(new IllegalArgumentException("Quantity must be positive"))
                .when(increaseStockOperation).execute(any(Game.class), eq(-5));

        assertThatThrownBy(() -> gameUseCase.increaseStock(id, -5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be positive");

        verify(gameRepositoryPort).findById(id);
        verify(increaseStockOperation).execute(game, -5);
        verify(gameRepositoryPort, never()).save(any(Game.class));
    }

    @Test
    void diminuirEstoque_deveDiminuirCorretamente() {
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);

        when(gameRepositoryPort.findById(id)).thenReturn(Optional.of(game));
        when(gameRepositoryPort.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

        doAnswer(invocation -> {
            Game g = invocation.getArgument(0);
            int q = invocation.getArgument(1);
            g.setStock(g.getStock() - q);
            return null;
        }).when(decreaseStockOperation).execute(any(Game.class), eq(3));

        Game result = gameUseCase.decreaseStock(id, 3);

        assertNotNull(result);
        assertEquals(7, result.getStock());

        verify(gameRepositoryPort).findById(id);
        verify(decreaseStockOperation).execute(game, 3);
        verify(gameRepositoryPort).save(game);
    }

    @Test
    void diminuirEstoque_deveLancarExcecaoQuandoQuantidadeForZero() {
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);

        when(gameRepositoryPort.findById(id)).thenReturn(Optional.of(game));

        doThrow(new IllegalArgumentException("Quantity must be positive"))
                .when(decreaseStockOperation).execute(any(Game.class), eq(0));

        assertThatThrownBy(() -> gameUseCase.decreaseStock(id, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be positive");

        verify(gameRepositoryPort).findById(id);
        verify(decreaseStockOperation).execute(game, 0);
        verify(gameRepositoryPort, never()).save(any(Game.class));
    }

    @Test
    void diminuirEstoque_deveLancarExcecaoQuandoQuantidadeNegativa() {
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);

        when(gameRepositoryPort.findById(id)).thenReturn(Optional.of(game));

        doThrow(new IllegalArgumentException("Quantity must be positive"))
                .when(decreaseStockOperation).execute(any(Game.class), eq(-5));

        assertThatThrownBy(() -> gameUseCase.decreaseStock(id, -5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be positive");

        verify(gameRepositoryPort).findById(id);
        verify(decreaseStockOperation).execute(game, -5);
        verify(gameRepositoryPort, never()).save(any(Game.class));
    }

    @Test
    void diminuirEstoque_deveLancarExcecaoQuandoQuantidadeMaiorQueEstoque() {
        Long id = 1L;
        Game game = GameFactoryBot.createGameWithId(id);

        when(gameRepositoryPort.findById(id)).thenReturn(Optional.of(game));

        doThrow(new IllegalArgumentException("Not enough stock for the game"))
                .when(decreaseStockOperation).execute(any(Game.class), eq(20));

        assertThatThrownBy(() -> gameUseCase.decreaseStock(id, 20))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Not enough stock for the game");

        verify(gameRepositoryPort).findById(id);
        verify(decreaseStockOperation).execute(game, 20);
        verify(gameRepositoryPort, never()).save(any(Game.class));
    }

    
}
