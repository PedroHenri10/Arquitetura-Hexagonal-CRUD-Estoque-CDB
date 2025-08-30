package com.cdb.estoque.adapter.input.mapper;

import org.mapstruct.Mapper;
import com.cdb.estoque.adapter.input.request.GameRequest;
import com.cdb.estoque.adapter.input.response.GameResponse;
import com.cdb.estoque.core.domain.model.Game;

@Mapper(componentModel = "spring")
public interface GameRestMapper {

    Game toDomain(GameRequest request);

    GameResponse toResponse(Game game);
}
