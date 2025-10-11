package com.cdb.estoque.core.domain.strategy;

import com.cdb.estoque.core.domain.model.Game;
import org.springframework.stereotype.Component;

@Component
public class DecreaseStockOperation implements StockOperation {
    @Override
    public void execute(Game game, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (game.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock for the game");
        }
        game.setStock(game.getStock() - quantity);
    }
}
