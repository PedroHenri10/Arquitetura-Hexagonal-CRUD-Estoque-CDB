package com.cdb.estoque.adapter.output.persistence;

import com.cdb.estoque.core.domain.model.Game;
import com.cdb.estoque.port.output.GameRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GamePersistenceAdapter implements GameRepositoryPort {

    private final GameJpaRepository gameJpaRepository;
    private final GamePersistenceMapper gamePersistenceMapper;

    @Override
    public List<Game> findAll() {

        List<GameEntity> allEntities = gameJpaRepository.findAll();
        return allEntities.stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Game> findById(Long id) {

        Optional<GameEntity> entityOptional = gameJpaRepository.findById(id);
        return entityOptional.map(mappper::toDomain);
    }


}
