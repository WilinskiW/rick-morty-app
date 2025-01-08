package com.rick_morty.rick_morty_updater.mapper;

public interface DtoEntityMapper<TDto, TEntity> {
    TEntity mapDtoToEntity(TDto dto);
}
