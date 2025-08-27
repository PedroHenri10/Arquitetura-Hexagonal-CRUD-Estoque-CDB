package com.cdb.estoque.port.input;

public interface GameInputPort {

    List<Game> findAll();
    Optional<Game> findById(long id);
    Game save (Game game);
    Game update(Long id, Game game);
    void deleteById(Long id);
}
