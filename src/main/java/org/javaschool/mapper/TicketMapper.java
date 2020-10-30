package org.javaschool.mapper;

import org.javaschool.dto.TicketDto;
import org.javaschool.entities.TicketEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {PassengerMapper.class, TrainMapper.class})
public interface TicketMapper {

    @Mapping(source = "departureTime", target = "departureTime", dateFormat = "HH:mm")
    @Mapping(source = "arrivalTime", target = "arrivalTime", dateFormat = "HH:mm")
    @Mapping(source = "date", target = "date", dateFormat = "dd.MM.yyyy")
    TicketDto toDto(TicketEntity ticket);

    @InheritConfiguration
    List<TicketDto> toDtoList(List<TicketEntity> tickets);

    @InheritInverseConfiguration
    TicketEntity toEntity(TicketDto ticketDto);
}