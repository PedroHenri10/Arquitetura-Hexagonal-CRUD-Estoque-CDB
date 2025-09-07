package com.cdb.estoque.port.output;

import com.cdb.estoque.core.domain.model.Game;

import java.util.List;
import java.util.Optional;

public interface GameRepositoryPort {
    List<Game> findAll();
    Optional<Game> findById(Long id);
    Game save (Game game);
    Game update(Long id, Game game);
    void deleteById(Long id);
    boolean existsById(Long id);
    List<Game> findByTitleContainingIgnoreCase(String gameTitle);
    List<Game> findyByGenreContainingIgnoreCase(String genre);
    List<Game> findyByPlataformContainingIgnoreCase(String plataform);
}