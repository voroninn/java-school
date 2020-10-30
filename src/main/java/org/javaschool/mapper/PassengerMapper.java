package org.javaschool.mapper;

import org.javaschool.dto.PassengerDto;
import org.javaschool.entities.PassengerEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {UserMapper.class, TicketMapper.class})
public interface PassengerMapper {

    @Mapping(source = "birthDate", target = "birthDate", dateFormat = "dd.MM.yyyy")
    PassengerDto toDto(PassengerEntity passenger);

    @InheritConfiguration
    List<PassengerDto> toDtoList(List<PassengerEntity> passengers);

    @InheritInverseConfiguration
    PassengerEntity toEntity(PassengerDto passengerDto);
}