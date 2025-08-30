package com.cdb.estoque.adapter.output.persistence;

import com.cdb.estoque.core.domain.model.Game;
import com.cdb.estoque.port.output.GameRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import com.cdb.estoque.adapter.output.entity.GameEntity;
import com.cdb.estoque.adapter.output.mapper.GamePersistanceMapper;
import com.cdb.estoque.core.domain.model.Game;
import com.cdb.estoque.port.output.GameRepositoryPort;
import lombok.RequiredArgsConstructor;

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


    @Override
    public Game save(Game game){
        GameEntity entity = mapper.toEntity(game);
        GameEntity savedEntity = gqmeJpaRepositoty.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long id){
        gameJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id){
        return gameJpaRepository.existsById(id);
    }
}
