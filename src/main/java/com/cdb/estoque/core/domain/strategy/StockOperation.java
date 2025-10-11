package com.cdb.estoque.core.domain.strategy;

import com.cdb.estoque.core.domain.model.Game;

public interface StockOperation {
    void execute(Game game, int quantity);
}