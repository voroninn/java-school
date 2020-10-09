package org.javaschool.dao.interfaces;

import org.javaschool.entities.TicketEntity;

import java.util.List;

public interface TicketDao {

    TicketEntity getTicket(int id);

    List<TicketEntity> getAllTickets();

    void addTicket(TicketEntity ticket);

    void editTicket(TicketEntity ticket);

    void deleteTicket(TicketEntity ticket);

    long getTicketCount();
}