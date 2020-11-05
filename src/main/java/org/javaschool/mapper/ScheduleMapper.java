package org.javaschool.mapper;

import org.javaschool.dto.ScheduleDto;
import org.javaschool.entities.ScheduleEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {StationMapper.class, TrainMapper.class})
public interface ScheduleMapper {

    @Mapping(source = "arrivalTime", target = "arrivalTime", dateFormat = "HH:mm")
    @Mapping(source = "departureTime", target = "departureTime", dateFormat = "HH:mm")
    ScheduleDto toDto(ScheduleEntity schedule);

    @InheritConfiguration
    List<ScheduleDto> toDtoList(List<ScheduleEntity> schedules);

    @InheritInverseConfiguration
    ScheduleEntity toEntity(ScheduleDto scheduleDto);

    @InheritInverseConfiguration
    List<ScheduleEntity> toEntityList(List<ScheduleDto> scheduleDtoList);
}