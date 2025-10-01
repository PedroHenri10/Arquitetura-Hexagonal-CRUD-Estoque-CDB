package com.cdb.estoque.factory;

import com.cdb.estoque.core.domain.model.Game;

import java.util.List;

public class GameFactoryBot {
    public static Game createDefaultGame() {
        return new Game(null, "Jogo Teste", "PC", "Ação", 150.0, 10);
    }

    public static Game createGameWithId(Long id) {
        return new Game(id, "Zelda", "Switch", "Aventura", 250.0, 10);
    }

    public static Game createCustomGame(Long id, String title, String platform, String genre, double price, int stock) {
        return new Game(id, title, platform, genre, price, stock);
    }

    public static Game createGameOutOfStock() {
        return new Game(null, "Sem Estoque", "PC", "Ação", 150.0, 0);
    }

    public static Game createGameWithNegativeStock() {
        return new Game(null, "Estoque Negativo", "PC", "Ação", 150.0, -5);
    }

    public static Game createGameWithZeroPrice() {
        return new Game(null, "Jogo Grátis", "PC", "Ação", 0.0, 10);
    }


    public static List<Game> createListOfGames() {
        return List.of(
                new Game(1L, "Jogo 1", "PC", "Ação", 150.0, 10),
                new Game(2L, "Jogo 2", "PC", "Drama", 180.0, 5),
                new Game(3L, "Jogo 3", "PlayStation", "Ação", 200.0, 3)
        );
    }


    public static Game createRandomGame() {
        long id = (long) (Math.random() * 1000);
        int stock = (int) (Math.random() * 50);
        double price = Math.random() * 500;
        return new Game(id, "Jogo Aleatório", "PC", "Ação", price, stock);
    }
}
