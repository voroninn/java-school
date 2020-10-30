package org.javaschool.mapper;

import org.javaschool.dto.MappingDto;
import org.javaschool.entities.MappingEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = {StationMapper.class, TrackMapper.class})
public interface MappingMapper {

    MappingDto toDto(MappingEntity mapping);

    List<MappingDto> toDtoList(List<MappingEntity> mappings);

    MappingEntity toEntity(MappingDto mappingDto);
}