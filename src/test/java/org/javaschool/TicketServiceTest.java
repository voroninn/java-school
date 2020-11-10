package org.javaschool;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.hibernate.loader.collection.OneToManyJoinWalker;
import org.javaschool.config.WebConfig;
import org.javaschool.dao.impl.TicketDaoImpl;
import org.javaschool.dao.interfaces.TicketDao;
import org.javaschool.dto.PassengerDto;
import org.javaschool.dto.TicketDto;
import org.javaschool.dto.TrainDto;
import org.javaschool.entities.TicketEntity;
import org.javaschool.entities.TrainEntity;
import org.javaschool.mapper.TicketMapper;
import org.javaschool.mapper.TicketMapperImpl;
import org.javaschool.services.impl.ScheduleServiceImpl;
import org.javaschool.services.impl.TicketServiceImpl;
import org.javaschool.services.interfaces.ScheduleService;
import org.javaschool.services.interfaces.TicketService;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Ignore
@RunWith(MockitoJUnitRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = {WebConfig.class})
public class TicketServiceTest {

//    @Autowired
//    @InjectMocks
//    private TicketService ticketService;
//
//    @Autowired
//    private TicketMapper ticketMapper;

    @Mock
    TicketMapper ticketMapper;

    @Mock
    ScheduleService scheduleService;

    @InjectMocks
    TicketServiceImpl ticketService;

    private static TicketDto testTicket;
    private static PassengerDto testPassenger;
    private static TrainDto testTrain;
    private static DateTimeFormatter timeFormatter;
    private static DateTimeFormatter dateFormatter;

    @BeforeClass
    public static void setUp() {
        timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        testPassenger = PassengerDto.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .birthDate("01.01.2001")
                .passportNumber(12345678)
                .build();
        testTrain = TrainDto.builder()
                .id(1)
                .name("Test I")
                .capacity(10)
                .build();
        testTicket = TicketDto.builder()
                .id(1)
                .number("NUMBER")
                .passenger(testPassenger)
                .trains(new HashSet<>())
                .departureStation("DEPARTURE")
                .arrivalStation("ARRIVAL")
                .departureTime("07:00")
                .arrivalTime("10:00")
                .date("08.11.2020")
                .price(0.0)
                .build();
        testTicket.getTrains().add(testTrain);
    }

    @Test
    public void testIsEnoughTimeToDepartureForToday() throws ParseException {
        testTicket.setDate(LocalDate.now().format(dateFormatter));
        testTicket.setDepartureTime(LocalTime.now().plusMinutes(15).format(timeFormatter));

//        when(ticketMapper.toEntity(testTicket).getDate())
//                .thenReturn(new SimpleDateFormat("dd.MM.yyyy").parse(testTicket.getDate()));
//        when(scheduleService.convertStringtoDate(testTicket.getDepartureTime()))
//                .thenReturn(new SimpleDateFormat("HH:mm").parse(testTicket.getDate()));

        assertTrue(ticketService.isEnoughTimeToDeparture(testTicket));

//        testTicket.setDepartureTime(LocalTime.now().plusMinutes(5).format(timeFormatter));
//        assertFalse(ticketService.isEnoughTimeToDeparture(testTicket));
    }

    @Test
    public void testIsEnoughTimeToDepartureForFutureDate() {
        testTicket.setDate(LocalDate.now().plusMonths(9).format(dateFormatter));
        testTicket.setDepartureTime(LocalTime.now().plusMinutes(15).format(timeFormatter));
        assertTrue(ticketService.isEnoughTimeToDeparture(testTicket));
        testTicket.setDepartureTime(LocalTime.now().plusMinutes(5).format(timeFormatter));
        assertTrue(ticketService.isEnoughTimeToDeparture(testTicket));
    }

    @Test
    public void testIsPassengerNotPresentOnTrainSuccess() {
        List<TicketDto> testTicketList = new ArrayList<>();
        testTicketList.add(testTicket);
        when(ticketService.getTicketsByTrainAndDate(any(TrainDto.class),
                any(Date.class))).thenReturn(testTicketList);
        verify(ticketService).getTicketsByTrainAndDate(any(TrainDto.class),
                any(Date.class));
        assertFalse(ticketService.isPassengerNotPresentOnTrain(testTicket));
    }

    @Test
    public void testIsPassengerNotPresentOnTrainFail() {
        List<TicketDto> testTicketList = new ArrayList<>();
        when(ticketService.getTicketsByTrainAndDate(any(TrainDto.class),
                any(Date.class))).thenReturn(testTicketList);
        assertTrue(ticketService.isPassengerNotPresentOnTrain(testTicket));
    }
}