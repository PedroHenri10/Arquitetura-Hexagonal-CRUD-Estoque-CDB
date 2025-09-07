package com.cdb.estoque.port.input;

import com.cdb.estoque.core.domain.model.Game;

import java.util.List;
import java.util.Optional;

public interface GameInputPort {

    List<Game> findAll();
    Optional<Game> findById(Long id);
    Game save (Game game);
    Game update(Long id, Game game);
    Game increaseStock(Long id, int quantity);
    Game decreaseStock(Long id, int quantity);
    List<Game> findByTitle(String titleGame);
    List<Game> findByGenre(String genre);
    List<Game> findByPlataform(String plataform);
    void deleteById(Long id);
}
