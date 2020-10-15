package org.javaschool.dao.interfaces;

import org.javaschool.entities.PassengerEntity;
import org.javaschool.entities.TicketEntity;
import org.javaschool.entities.TrainEntity;

import java.util.Date;
import java.util.List;

public interface TicketDao {

    TicketEntity getTicket(int id);

    List<TicketEntity> getAllTickets();

    void addTicket(TicketEntity ticket);

    void editTicket(TicketEntity ticket);

    void deleteTicket(TicketEntity ticket);

    long getTicketCount();

    List<TicketEntity> getTicketsByPassenger(PassengerEntity passenger);

    List<TicketEntity> getTicketsByTrainAndDate(TrainEntity train, Date date);
}