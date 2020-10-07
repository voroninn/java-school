package org.javaschool.services.impl;

import org.javaschool.dao.interfaces.TicketDao;
import org.javaschool.entities.TicketEntity;
import org.javaschool.services.interfaces.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDao ticketDao;

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
}
