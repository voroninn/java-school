package org.javaschool.services.impl;

import lombok.extern.log4j.Log4j2;
import org.javaschool.dao.interfaces.TicketDao;
import org.javaschool.dto.*;
import org.javaschool.mapper.PassengerMapper;
import org.javaschool.mapper.TicketMapper;
import org.javaschool.mapper.TrainMapper;
import org.javaschool.services.interfaces.ScheduleService;
import org.javaschool.services.interfaces.SectionService;
import org.javaschool.services.interfaces.StationService;
import org.javaschool.services.interfaces.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private StationService stationService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private PassengerMapper passengerMapper;

    @Autowired
    private TrainMapper trainMapper;

    @Override
    @Transactional
    public TicketDto getTicket(int id) {
        return ticketMapper.toDto(ticketDao.getTicket(id));
    }

    @Override
    @Transactional
    public List<TicketDto> getAllTickets() {
        return ticketMapper.toDtoList(ticketDao.getAllTickets());
    }

    @Override
    @Transactional
    public void addTicket(TicketDto ticketDto) {
        ticketDao.addTicket(ticketMapper.toEntity(ticketDto));
        log.info("Created new ticket " + ticketDto.getNumber());
    }

    @Override
    @Transactional
    public void editTicket(TicketDto ticketDto) {
        ticketDao.editTicket(ticketMapper.toEntity(ticketDto));
        log.info("Edited ticket " + ticketDto.getNumber());
    }

    @Override
    @Transactional
    public void deleteTicket(TicketDto ticketDto) {
        ticketDao.deleteTicket(ticketMapper.toEntity(ticketDto));
        log.info("Deleted ticket " + ticketDto.getNumber());
    }

    @Override
    @Transactional
    public long getTicketCount() {
        return ticketDao.getTicketCount();
    }

    @Override
    public String generateTicketNumber(TicketDto ticketDto) {
        return String.valueOf(ticketDto.getDepartureStation().charAt(0)) +
                ticketDto.getArrivalStation().charAt(0) +
                (ticketDto.getPassenger().getId() + (int) (Math.random() * ((99 - 1) + 1)) + 1) +
                (getTicketCount() + (int) (Math.random() * ((99 - 1) + 1)) + 1);
    }

    @Override
    public double calculateTicketPrice(List<StationDto> route) {
        double ticketPrice = 0;
        List<SectionDto> sections = sectionService.getSectionsByRoute(route);
        for (SectionDto section : sections) {
            ticketPrice += section.getLength() * 3;
        }
        return ticketPrice;
    }

    @Override
    @Transactional
    public List<TicketDto> getTicketsByPassenger(PassengerDto passengerDto) {
        return ticketMapper.toDtoList(ticketDao.getTicketsByPassenger(passengerMapper.toEntity(passengerDto)));
    }

    @Override
    @Transactional
    public List<TicketDto> getTicketsByTrainAndDate(TrainDto trainDto, Date date) {
        return ticketMapper.toDtoList(ticketDao.getTicketsByTrainAndDate(trainMapper.toEntity(trainDto), date));
    }

    @Override
    public boolean areTicketsAvailable(TicketDto ticketDto) {
        boolean areTicketsAvailable = true;
        Date departureTime = scheduleService.convertStringtoDate(ticketDto.getDepartureTime());
        Date arrivalTime = scheduleService.convertStringtoDate(ticketDto.getArrivalTime());
        Date date = scheduleService.convertStringtoDate(ticketDto.getDate());
        List<SectionDto> sections = sectionService.getSectionsByRoute
                (stationService.getRoute(ticketDto.getDepartureStation(), ticketDto.getArrivalStation()));
        List<TrainDto> trainDtoList = new ArrayList<>(ticketDto.getTrains());
        for (TrainDto train : trainDtoList) {
            List<TicketDto> existingTickets = getTicketsByTrainAndDate(train, date);
            int existingTicketsCount = 0;
            for (SectionDto section : sections) {
                if (section.getTrack().equals(train.getTrack())) {
                    for (TicketDto ticketItem : existingTickets) {
                        if (scheduleService.convertStringtoDate(ticketItem.getDepartureTime()).before(arrivalTime) &&
                                scheduleService.convertStringtoDate(ticketItem.getArrivalTime()).after(departureTime)) {
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
    public boolean isPassengerNotPresentOnTrain(TicketDto ticketDto) {
        List<TrainDto> trainDtoList = new ArrayList<>(ticketDto.getTrains());
        Date date = scheduleService.convertStringtoDate(ticketDto.getDate());
        boolean isPassengerNotPresentOnTrain = true;
        for (TrainDto trainDto : trainDtoList) {
            List<TicketDto> existingTickets = getTicketsByTrainAndDate(trainDto, date);
            for (TicketDto ticketItem : existingTickets) {
                if (ticketItem.getPassenger().getFirstName().equals(ticketDto.getPassenger().getFirstName()) &&
                        ticketItem.getPassenger().getLastName().equals(ticketDto.getPassenger().getLastName()) &&
                        ticketItem.getPassenger().getBirthDate().equals(ticketDto.getPassenger().getBirthDate())) {
                    isPassengerNotPresentOnTrain = false;
                    break;
                }
            }
        }
        return isPassengerNotPresentOnTrain;
    }

    @Override
    public boolean isEnoughTimeToDeparture(TicketDto ticketDto) {
        if (!ticketMapper.toEntity(ticketDto).getDate().after(new Date())) {
            LocalTime current = LocalTime.now(ZoneId.of("Europe/Moscow"));
            LocalTime departureTime = scheduleService.convertStringtoDate(ticketDto.getDepartureTime())
                    .toInstant().atZone(ZoneId.of("Europe/Moscow")).toLocalTime();
            return Duration.between(current, departureTime).toMillis() >= 600000;
        } else {
            return true;
        }
    }

    @Override
    public List<String> validateTicket(TicketDto ticketDto) {
        List<String> validationMessages = new ArrayList<>();
        if (!areTicketsAvailable(ticketDto)) {
            validationMessages.add("No more tickets available for this train.");
        }
        if (!isPassengerNotPresentOnTrain(ticketDto)) {
            validationMessages.add("You already have a ticket for this train.");
        }
        if (!isEnoughTimeToDeparture(ticketDto)) {
            validationMessages.add("Less than 10 minutes left until departure.");
        }
        if (areTicketsAvailable(ticketDto) && isPassengerNotPresentOnTrain(ticketDto)
                && isEnoughTimeToDeparture(ticketDto)) {
            validationMessages.add("success");
        }
        return validationMessages;
    }
}