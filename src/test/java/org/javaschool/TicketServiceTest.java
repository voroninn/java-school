package org.javaschool;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.javaschool.dto.PassengerDto;
import org.javaschool.dto.TicketDto;
import org.javaschool.dto.TrainDto;
import org.javaschool.entities.TicketEntity;
import org.javaschool.mapper.TicketMapper;
import org.javaschool.services.impl.TicketServiceImpl;
import org.javaschool.services.interfaces.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Disabled
@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Mock
    private TicketMapper ticketMapper;

    @Mock
    private ScheduleService scheduleService;

    private TicketDto testTicketDto;
    private PassengerDto testPassenger;
    private TrainDto testTrain;
    private DateTimeFormatter timeFormatter;
    private DateTimeFormatter dateFormatter;

    @BeforeEach
    public void setUp() {
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
                .capacity(3)
                .build();
        testTicketDto = TicketDto.builder()
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
        testTicketDto.getTrains().add(testTrain);
    }

    @Test
    public void testGetTicket() {
        when(ticketService.getTicket(anyInt())).thenReturn(testTicketDto);
        assertEquals(testTicketDto, ticketService.getTicket(1));
    }

    @Test
    public void testIsDateAfter() {
        TicketEntity testTicketEnt = new TicketEntity();
        testTicketEnt.setDate(new Date());
        when(ticketMapper.toEntity(testTicketDto)).thenReturn(testTicketEnt);
        //assertFalse(ticketService.isDa);
    }

    @Test
    public void testIsEnoughTimeToDepartureForToday() throws ParseException {
        TicketEntity testTicketEnt = new TicketEntity();
        testTicketEnt.setDate(new Date());
        testTicketDto.setDate(LocalDate.now().format(dateFormatter));
        testTicketDto.setDepartureTime(LocalTime.now().plusMinutes(15).format(timeFormatter));

        when(ticketMapper.toEntity(testTicketDto)).thenReturn(testTicketEnt);
        when(scheduleService.convertStringtoDate(testTicketDto.getDepartureTime()))
                .thenReturn(new SimpleDateFormat("HH:mm").parse(testTicketDto.getDepartureTime()));

        assertTrue(ticketService.isEnoughTimeToDeparture(testTicketDto));

//        testTicket.setDepartureTime(LocalTime.now().plusMinutes(5).format(timeFormatter));
//        assertFalse(ticketService.isEnoughTimeToDeparture(testTicket));
    }

    @Test
    public void testIsEnoughTimeToDepartureForFutureDate() {
        testTicketDto.setDate(LocalDate.now().plusMonths(9).format(dateFormatter));
        testTicketDto.setDepartureTime(LocalTime.now().plusMinutes(15).format(timeFormatter));
        assertTrue(ticketService.isEnoughTimeToDeparture(testTicketDto));
        testTicketDto.setDepartureTime(LocalTime.now().plusMinutes(5).format(timeFormatter));
        assertTrue(ticketService.isEnoughTimeToDeparture(testTicketDto));
    }

    @Test
    public void testIsPassengerNotPresentOnTrainSuccess() {
        List<TicketDto> testTicketList = new ArrayList<>();
        testTicketList.add(testTicketDto);
        when(ticketService.getTicketsByTrainAndDate(any(TrainDto.class),
                any(Date.class))).thenReturn(testTicketList);
        verify(ticketService).getTicketsByTrainAndDate(any(TrainDto.class),
                any(Date.class));
        assertFalse(ticketService.isPassengerNotPresentOnTrain(testTicketDto));
    }

    @Test
    public void testIsPassengerNotPresentOnTrainFail() {
        List<TicketDto> testTicketList = new ArrayList<>();
        when(ticketService.getTicketsByTrainAndDate(any(TrainDto.class),
                any(Date.class))).thenReturn(testTicketList);
        assertTrue(ticketService.isPassengerNotPresentOnTrain(testTicketDto));
    }
}