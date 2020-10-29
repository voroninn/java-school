package org.javaschool.mapper;

import org.javaschool.dto.ScheduleDto;
import org.javaschool.entities.ScheduleEntity;
import org.mapstruct.Mapper;

@Mapper(uses = {StationMapper.class, TrainMapper.class})
public interface ScheduleMapper {

    ScheduleDto toDto(ScheduleEntity schedule);

    ScheduleEntity toEntity(ScheduleDto scheduleDto);
}