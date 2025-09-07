package com.cdb.estoque.adapter.output.persistence;

import com.cdb.estoque.core.domain.model.Game;
import com.cdb.estoque.port.output.GameRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.cdb.estoque.adapter.output.entity.GameEntity;
import com.cdb.estoque.adapter.output.mapper.GamePersistanceMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GamePersistenceAdapter implements GameRepositoryPort {

    private final DataGameRepository dataGameRepository;
    private final GamePersistanceMapper mapper;

    @Override
    public List<Game> findAll() {

        List<GameEntity> allEntities = dataGameRepository.findAll();
        return allEntities.stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Game> findById(Long id) {

        Optional<GameEntity> entityOptional = dataGameRepository.findById(id);
        return entityOptional.map(mapper::toDomain);
    }


    @Override
    public Game save(Game game){
        GameEntity entity = mapper.toEntity(game);
        GameEntity savedEntity = dataGameRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public  Game update(Long id, Game game){
        game.setId(id);

        GameEntity entity = mapper.toEntity(game);
        GameEntity updatedEntity = dataGameRepository.save(entity);
        return mapper.toDomain(updatedEntity);
    }

    @Override
    public List<Game> findByTitleContainingIgnoreCase(String titleGame){
        List<GameEntity> entities = dataGameRepository.findByTitleContainingIgnoreCase(titleGame);

        return entities.stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Game> findByGenreContainingIgnoreCase(String genre){
        List<GameEntity> entities = dataGameRepository.findByGenreContainingIgnoreCase(genre);

        return entities.stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Game> findByPlataformContainingIgnoreCase(String plataform){
        List<GameEntity> entities = dataGameRepository.findByPlataformContainingIgnoreCase(plataform);

        return entities.stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id){
        dataGameRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id){
        return dataGameRepository.existsById(id);
    }
}
