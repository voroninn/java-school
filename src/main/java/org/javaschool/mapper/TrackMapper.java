package org.javaschool.mapper;

import org.javaschool.dto.TrackDto;
import org.javaschool.entities.TrackEntity;
import org.mapstruct.Mapper;

@Mapper(uses = {TrainMapper.class, MappingMapper.class})
public interface TrackMapper {

    TrackDto toDto(TrackEntity track);

    TrackEntity toEntity(TrackDto trackDto);
}