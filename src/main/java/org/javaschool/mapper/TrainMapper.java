package org.javaschool.mapper;

import org.javaschool.dto.TrainDto;
import org.javaschool.entities.TrainEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = {ScheduleMapper.class, TicketMapper.class, TrackMapper.class})
public interface TrainMapper {

    TrainDto toDto(TrainEntity train);

    List<TrainDto> toDtoList(List<TrainEntity> trains);

    TrainEntity toEntity(TrainDto trainDto);
}