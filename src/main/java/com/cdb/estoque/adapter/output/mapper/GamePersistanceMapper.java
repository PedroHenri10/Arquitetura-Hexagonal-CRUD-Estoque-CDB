package com.cdb.estoque.adapter.output.mapper;

import com.cdb.estoque.adapter.output.entity.GameEntity;
import com.cdb.estoque.core.domain.model.Game;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GamePersistanceMapper {
    GameEntity toEntity(Game game);

    Game toDomain(GameEntity entity);
}
