package com.guner.questapp.services.mapper;

import java.util.List;

public interface IBaseMapper<Entity, DTO> {

    DTO entityToDto(Entity entity);

    Entity dtoToEntity(DTO dto);

    List<DTO> entityListToDtoList(List<Entity> entity);

    List<Entity> dtoListToEntityList(List<DTO> dtoList);

}
