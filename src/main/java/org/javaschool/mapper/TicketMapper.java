package org.javaschool.mapper;

import org.javaschool.dto.TicketDto;
import org.javaschool.entities.TicketEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {PassengerMapper.class, TrainMapper.class})
public interface TicketMapper {

    @Mapping(source = "date", target = "date", dateFormat = "dd.MM.yyyy")
    TicketDto toDto(TicketEntity ticket);

    @InheritInverseConfiguration
    TicketEntity toEntity(TicketDto ticketDto);
}