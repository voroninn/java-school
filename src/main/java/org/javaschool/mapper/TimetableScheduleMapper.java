package org.javaschool.mapper;

import org.javaschool.dto.ScheduleDto;
import org.javaschool.dto.TimetableScheduleDto;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface TimetableScheduleMapper {

    @Mapping(source = "station.name", target = "stationName")
    @Mapping(source = "train.name", target = "trainName")
    TimetableScheduleDto toDto(ScheduleDto scheduleDto);

    @InheritConfiguration
    List<TimetableScheduleDto> toDtoList(List<ScheduleDto> scheduleDtoList);
}