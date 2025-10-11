package com.cdb.estoque.core.domain.factory;

import com.cdb.estoque.core.domain.model.Game;
import org.springframework.stereotype.Component;

@Component
public class GameFactory {

    public Game createGame(String titleGame, String plataform, String genre, Double price, Integer stock) {
        
        if (titleGame == null || titleGame.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        return new Game(null, titleGame, plataform, genre, price, stock);
    }

    public Game createGameWithId(Long id, String titleGame, String plataform, String genre, Double price, Integer stock) {
        Game game = createGame(titleGame, plataform, genre, price, stock);
        game.setId(id);
        return game;
    }
}
