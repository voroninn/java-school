package org.javaschool.mapper;

import org.javaschool.dto.RoleDto;
import org.javaschool.entities.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(uses = {UserMapper.class})
public interface RoleMapper {

    RoleDto toDto(RoleEntity role);

    RoleEntity toEntity(RoleDto roleDto);
}
