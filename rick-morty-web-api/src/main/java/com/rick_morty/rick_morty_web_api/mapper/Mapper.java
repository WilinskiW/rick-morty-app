package com.rick_morty.rick_morty_web_api.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<TEntity, TDto> {
    TDto entityToDto(TEntity entity);

    default List<TDto> entityListToDtoList(List<TEntity> entityList){
        return entityList.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    TEntity dtoToEntity(TDto dto);
}
