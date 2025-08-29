package com.cdb.estoque.adapter.input.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GameMapper {

    org.mapstruct.factory.Mappers.getMapper(GameMapper.class);
}
