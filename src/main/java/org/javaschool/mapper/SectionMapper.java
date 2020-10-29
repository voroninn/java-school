package org.javaschool.mapper;

import org.javaschool.dto.SectionDto;
import org.javaschool.entities.SectionEntity;
import org.mapstruct.Mapper;

@Mapper(uses = {StationMapper.class, TrackMapper.class})
public interface SectionMapper {

    SectionDto toDto(SectionEntity section);

    SectionEntity toEntity(SectionDto sectionDto);
}