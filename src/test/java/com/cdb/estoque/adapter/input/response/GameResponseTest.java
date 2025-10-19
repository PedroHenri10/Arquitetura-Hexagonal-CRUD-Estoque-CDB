package com.cdb.estoque.adapter.input.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameResponseTest {

    @Test
    @DisplayName("Deve testar getters e setters")
    void shouldTestGettersAndSetters() {
        Long id = 1L;
        String titleGame = "The Witcher 3";
        String plataform = "PC";
        String genre = "RPG";
        Double price = 99.99;
        Integer stock = 10;

        GameResponse gameResponse = new GameResponse();

        gameResponse.setId(id);
        gameResponse.setTitleGame(titleGame);
        gameResponse.setPlataform(plataform);
        gameResponse.setGenre(genre);
        gameResponse.setPrice(price);
        gameResponse.setStock(stock);

        assertThat(gameResponse.getId()).isEqualTo(id);
        assertThat(gameResponse.getTitleGame()).isEqualTo(titleGame);
        assertThat(gameResponse.getPlataform()).isEqualTo(plataform);
        assertThat(gameResponse.getGenre()).isEqualTo(genre);
        assertThat(gameResponse.getPrice()).isEqualTo(price);
        assertThat(gameResponse.getStock()).isEqualTo(stock);
    }

    @Test
    @DisplayName("Deve testar construtor AllArgsConstructor e Builder")
    void shouldTestAllArgsConstructorAndBuilder() {
        Long id = 2L;
        String titleGame = "Cyberpunk 2077";
        String plataform = "PS5";
        String genre = "RPG de Ação";
        Double price = 199.90;
        Integer stock = 5;

        GameResponse gameResponseWithArgs = new GameResponse(id, titleGame, plataform, genre, price, stock);

        assertThat(gameResponseWithArgs.getId()).isEqualTo(id);
        assertThat(gameResponseWithArgs.getTitleGame()).isEqualTo(titleGame);
        assertThat(gameResponseWithArgs.getPlataform()).isEqualTo(plataform);
        assertThat(gameResponseWithArgs.getGenre()).isEqualTo(genre);
        assertThat(gameResponseWithArgs.getPrice()).isEqualTo(price);
        assertThat(gameResponseWithArgs.getStock()).isEqualTo(stock);

        GameResponse gameResponseBuilder = GameResponse.builder()
                .id(id)
                .titleGame(titleGame)
                .plataform(plataform)
                .genre(genre)
                .price(price)
                .stock(stock)
                .build();

        assertThat(gameResponseBuilder.getId()).isEqualTo(id);
        assertThat(gameResponseBuilder.getTitleGame()).isEqualTo(titleGame);
        assertThat(gameResponseBuilder.getPlataform()).isEqualTo(plataform);
        assertThat(gameResponseBuilder.getGenre()).isEqualTo(genre);
        assertThat(gameResponseBuilder.getPrice()).isEqualTo(price);
        assertThat(gameResponseBuilder.getStock()).isEqualTo(stock);
    }

    @Test
    @DisplayName("Deve testar o método equals() e hashCode()")
    void shouldTestEqualsAndHashCode() {
        GameResponse game1 = GameResponse.builder()
                .id(1L)
                .titleGame("Game A")
                .plataform("PC")
                .genre("Ação")
                .price(50.0)
                .stock(5)
                .build();

        GameResponse game2 = GameResponse.builder()
                .id(1L)
                .titleGame("Game A")
                .plataform("PC")
                .genre("Ação")
                .price(50.0)
                .stock(5)
                .build();

        GameResponse game3 = GameResponse.builder()
                .id(2L)
                .titleGame("Game B")
                .plataform("PS4")
                .genre("RPG")
                .price(60.0)
                .stock(3)
                .build();

        assertThat(game1).isEqualTo(game2);
        assertThat(game1).isNotEqualTo(game3);
        assertThat(game1).isNotEqualTo(null);
        assertThat(game1).isNotEqualTo(new Object());

        assertThat(game1.hashCode()).isEqualTo(game2.hashCode());
        assertThat(game1.hashCode()).isNotEqualTo(game3.hashCode());
    }

    @Test
    @DisplayName("Deve testar o método toString()")
    void shouldTestToString() {
        GameResponse gameResponse = GameResponse.builder()
                .id(1L)
                .titleGame("Game A")
                .plataform("PC")
                .genre("Ação")
                .price(50.0)
                .stock(5)
                .build();

        String toStringResult = gameResponse.toString();

        assertThat(toStringResult)
                .contains("id=1")
                .contains("titleGame=Game A")
                .contains("plataform=PC")
                .contains("genre=Ação")
                .contains("price=50.0")
                .contains("stock=5");
    }
}