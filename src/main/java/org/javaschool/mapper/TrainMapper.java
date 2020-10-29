package org.javaschool.mapper;

import org.javaschool.dto.TrainDto;
import org.javaschool.entities.TrainEntity;
import org.mapstruct.Mapper;

@Mapper(uses = {ScheduleMapper.class, TicketMapper.class, TrackMapper.class})
public interface TrainMapper {

    TrainDto toDto(TrainEntity train);

    TrainEntity toEntity(TrainDto trainDto);
}