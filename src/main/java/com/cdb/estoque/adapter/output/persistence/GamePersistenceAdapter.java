package com.cdb.estoque.adapter.output.persistence;

import com.cdb.estoque.core.domain.model.Game;
import com.cdb.estoque.port.output.GameRepositoryPort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class GamePersistenceAdapter implements GameRepositoryPort {

    @Override
    public List<Game> findAll() {
        return List.of();
    }

    @Override
    public Optional<Game> findById(Long id) {
        return Optional.empty();
    }

    
}
