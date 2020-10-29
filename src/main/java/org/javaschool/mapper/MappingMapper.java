package org.javaschool.mapper;

import org.javaschool.dto.MappingDto;
import org.javaschool.entities.MappingEntity;
import org.mapstruct.Mapper;

@Mapper(uses = {StationMapper.class, TrackMapper.class})
public interface MappingMapper {

    MappingDto toDto(MappingEntity mapping);

    MappingEntity toEntity(MappingDto mappingDto);
}