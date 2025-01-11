package com.rick_morty.rick_morty_web_api.mapper;

import java.util.List;

public interface Mapper<TEntity, TDto> {
    TDto entityToDto(TEntity entity);

    List<TDto> entityListToDtoList(List<TEntity> entityList);

    TEntity dtoToEntity(TDto dto);
}
