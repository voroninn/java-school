package org.javaschool.mapper;

import org.javaschool.dto.StationDto;
import org.javaschool.entities.StationEntity;
import org.mapstruct.Mapper;

@Mapper(uses = {ScheduleMapper.class, MappingMapper.class})
public interface StationMapper {

    StationDto toDto(StationEntity station);

    StationEntity toEntity(StationDto trainDto);
}