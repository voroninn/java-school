package org.javaschool.services.interfaces;

import org.javaschool.dto.PassengerDto;
import org.javaschool.dto.StationDto;
import org.javaschool.dto.TicketDto;
import org.javaschool.dto.TrainDto;

import java.util.Date;
import java.util.List;

public interface TicketService {

    TicketDto getTicket(int id);

    List<TicketDto> getAllTickets();

    void addTicket(TicketDto ticketDto);

    void editTicket(TicketDto ticketDto);

    void deleteTicket(TicketDto ticketDto);

    long getTicketCount();

    String generateTicketNumber(TicketDto ticketDto);

    double calculateTicketPrice(List<StationDto> route);

    List<TicketDto> getTicketsByPassenger(PassengerDto passengerDto);

    List<TicketDto> getTicketsByTrainAndDate(TrainDto trainDto, Date date);

    boolean areTicketsAvailable(TicketDto ticketDto);

    boolean isPassengerNotPresentOnTrain(TicketDto ticketDto);

    boolean isEnoughTimeToDeparture(TicketDto ticketDto);

    List<String> validateTicket(TicketDto ticketDto);
}