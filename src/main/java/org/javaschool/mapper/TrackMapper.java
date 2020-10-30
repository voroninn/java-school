package org.javaschool.mapper;

import org.javaschool.dto.TrackDto;
import org.javaschool.entities.TrackEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = {TrainMapper.class, MappingMapper.class})
public interface TrackMapper {

    TrackDto toDto(TrackEntity track);

    List<TrackDto> toDtoList(List<TrackEntity> tracks);

    TrackEntity toEntity(TrackDto trackDto);
}