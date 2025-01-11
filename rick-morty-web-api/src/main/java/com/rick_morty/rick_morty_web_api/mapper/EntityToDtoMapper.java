package com.rick_morty.rick_morty_web_api.mapper;

public interface EntityToDtoMapper<TEntity, TDto> {
    TDto entityToDto(TEntity entity);
}
