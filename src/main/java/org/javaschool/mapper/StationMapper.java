package org.javaschool.mapper;

import org.javaschool.dto.StationDto;
import org.javaschool.entities.StationEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = {ScheduleMapper.class, MappingMapper.class})
public interface StationMapper {

    StationDto toDto(StationEntity station);

    List<StationDto> toDtoList(List<StationEntity> stations);

    StationEntity toEntity(StationDto trainDto);

    List<StationEntity> toEntityList(List<StationDto> stationDtoList);
}