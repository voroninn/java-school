package org.javaschool.services.impl;

import org.javaschool.dao.interfaces.TicketDao;
import org.javaschool.entities.*;
import org.javaschool.services.interfaces.SectionService;
import org.javaschool.services.interfaces.StationService;
import org.javaschool.services.interfaces.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private StationService stationService;

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
    @Transactional
    public long getTicketCount() {
        return ticketDao.getTicketCount();
    }

    @Override
    public String generateTicketNumber(TicketEntity ticket) {
        return String.valueOf(ticket.getDepartureStation().charAt(0)) +
                ticket.getArrivalStation().charAt(0) +
                (ticket.getPassenger().getId() + (int) (Math.random() * ((99 - 1) + 1)) + 1) +
                (getTicketCount() + (int) (Math.random() * ((99 - 1) + 1)) + 1);
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
    @Transactional
    public List<TicketEntity> getTicketsByPassenger(PassengerEntity passenger) {
        return ticketDao.getTicketsByPassenger(passenger);
    }

    @Override
    @Transactional
    public List<TicketEntity> getTicketsByTrainAndDate(TrainEntity train, Date date) {
        return ticketDao.getTicketsByTrainAndDate(train, date);
    }

    @Override
    public boolean areTicketsAvailable(TicketEntity ticket) {
        boolean areTicketsAvailable = true;
        Date departureTime = ticket.getDepartureTime();
        Date arrivalTime = ticket.getArrivalTime();
        Date date = ticket.getDate();
        List<SectionEntity> sections = sectionService.getSectionsByRoute
                (stationService.getRoute(ticket.getDepartureStation(), ticket.getArrivalStation()));
        List<TrainEntity> trains = new ArrayList<>(ticket.getTrains());

        for (TrainEntity train : trains) {
            List<TicketEntity> existingTickets = getTicketsByTrainAndDate(train, date);
            int existingTicketsCount = 0;
            for (SectionEntity section : sections) {
                if (section.getTrack().equals(train.getTrack())) {
                    for (TicketEntity ticketItem : existingTickets) {
                        if (ticketItem.getDepartureTime().before(arrivalTime) &&
                                ticketItem.getArrivalTime().after(departureTime)) {
                            existingTicketsCount++;
                        }
                    }
                }
                if (existingTicketsCount == train.getCapacity()) {
                    areTicketsAvailable = false;
                    break;
                }
            }
        }
        return areTicketsAvailable;
    }

    @Override
    public boolean isPassengerNotPresentOnTrain(TicketEntity ticket) {
        List<TrainEntity> trains = new ArrayList<>(ticket.getTrains());
        Date date = ticket.getDate();
        boolean isPassengerNotPresentOnTrain = true;

        for (TrainEntity train : trains) {
            List<TicketEntity> existingTickets = getTicketsByTrainAndDate(train, date);
            for (TicketEntity ticketItem : existingTickets) {
                if (ticketItem.getPassenger().getFirstName().equals(ticket.getPassenger().getFirstName()) &&
                        ticketItem.getPassenger().getLastName().equals(ticket.getPassenger().getLastName()) &&
                        ticketItem.getPassenger().getBirthDate().equals(ticket.getPassenger().getBirthDate())) {
                    isPassengerNotPresentOnTrain = false;
                    break;
                }
            }
        }
        return isPassengerNotPresentOnTrain;
    }

    @Override
    public boolean isEnoughTimeToDeparture(TicketEntity ticket) {
        if (!ticket.getDate().after(new Date())) {
            LocalTime current = LocalTime.now(ZoneId.of("Europe/Moscow"));
            LocalTime departureTime = ticket.getDepartureTime().toInstant().atZone(ZoneId.of("Europe/Moscow")).toLocalTime();
            return Duration.between(current, departureTime).toMillis() >= 600000;
        } else {
            return true;
        }
    }

    @Override
    public List<String> validateTicket(TicketEntity ticket) {
        List<String> validationMessages = new ArrayList<>();
        if (!areTicketsAvailable(ticket)) {
            validationMessages.add("No more tickets available for this train.");
        }
        if (!isPassengerNotPresentOnTrain(ticket)) {
            validationMessages.add("You already have a ticket for this train.");
        }
        if (!isEnoughTimeToDeparture(ticket)) {
            validationMessages.add("Less than 10 minutes left until departure.");
        }
        if (areTicketsAvailable(ticket) && isPassengerNotPresentOnTrain(ticket) && isEnoughTimeToDeparture(ticket)) {
            validationMessages.add("success");
        }
        return validationMessages;
    }
}