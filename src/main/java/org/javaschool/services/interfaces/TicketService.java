package org.javaschool.services.interfaces;

import org.javaschool.entities.PassengerEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TicketEntity;

import java.util.List;

public interface TicketService {

    TicketEntity getTicket(int id);

    List<TicketEntity> getAllTickets();

    void addTicket(TicketEntity ticket);

    void editTicket(TicketEntity ticket);

    void deleteTicket(TicketEntity ticket);

    long getTicketCount();

    String generateTicketNumber(TicketEntity ticket);

    double calculateTicketPrice(List<StationEntity> route);

    List<TicketEntity> getTicketsByPassenger(PassengerEntity passenger);
}