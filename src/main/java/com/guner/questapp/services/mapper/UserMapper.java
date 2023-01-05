package com.guner.questapp.services.mapper;

import com.guner.questapp.entities.User;
import com.guner.questapp.requests.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper{

    User dtoToEntity(UserDto userDto);

    UserDto entityToDto(User user);

    List<UserDto> entityListToDtoList(List<User> products);

    List<User> dtoListToEntityList(List<UserDto> userDtos);

}
