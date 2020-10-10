package org.javaschool.services.impl;

import org.javaschool.dao.interfaces.TicketDao;
import org.javaschool.entities.PassengerEntity;
import org.javaschool.entities.SectionEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TicketEntity;
import org.javaschool.services.interfaces.SectionService;
import org.javaschool.services.interfaces.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private SectionService sectionService;

    @Override
    @Transactional
    public TicketEntity getTicket(int id) {
        return ticketDao.getTicket(id);
    }

    @Override
    @Transactional
    public List<TicketEntity> getAllTickets() {
        return ticketDao.getAllTickets();
    }

    @Override
    @Transactional
    public void addTicket(TicketEntity ticket) {
        ticketDao.addTicket(ticket);
    }

    @Override
    @Transactional
    public void editTicket(TicketEntity ticket) {
        ticketDao.editTicket(ticket);
    }

    @Override
    @Transactional
    public void deleteTicket(TicketEntity ticket) {
        ticketDao.deleteTicket(ticket);
    }

    @Override
    public long getTicketCount() {
        return ticketDao.getTicketCount();
    }

    @Override
    public String generateTicketNumber(TicketEntity ticket) {
        return String.valueOf(ticket.getDepartureStation().charAt(0)) +
                ticket.getArrivalStation().charAt(0) +
                ticket.getPassenger().getId() +
                getTicketCount();
    }

    @Override
    public double calculateTicketPrice(List<StationEntity> route) {
        double ticketPrice = 0;
        List<SectionEntity> sections = sectionService.getSectionsByRoute(route);
        for (SectionEntity section : sections) {
            ticketPrice += section.getLength() * 3;
        }
        return ticketPrice;
    }

    @Override
    public List<TicketEntity> getTicketsByPassenger(PassengerEntity passenger) {
        return ticketDao.getTicketsByPassenger(passenger);
    }
}
