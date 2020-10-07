package org.javaschool.services.interfaces;

import org.javaschool.entities.TicketEntity;

import java.util.List;

public interface TicketService {

    TicketEntity getTicket(int id);

    List<TicketEntity> getAllTickets();

    void addTicket(TicketEntity ticket);

    void editTicket(TicketEntity ticket);

    void deleteTicket(TicketEntity ticket);
}
