package org.javaschool.mapper;

import org.javaschool.dto.UserDto;
import org.javaschool.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(uses = {PassengerMapper.class, RoleMapper.class})
public interface UserMapper {

    UserDto toDto(UserEntity user);

    UserEntity toEntity(UserDto userDto);
}