package org.javaschool.mapper;

import org.javaschool.dto.SectionDto;
import org.javaschool.entities.SectionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = {StationMapper.class, TrackMapper.class})
public interface SectionMapper {

    SectionDto toDto(SectionEntity section);

    List<SectionDto> toDtoList(List<SectionEntity> sections);

    SectionEntity toEntity(SectionDto sectionDto);
}