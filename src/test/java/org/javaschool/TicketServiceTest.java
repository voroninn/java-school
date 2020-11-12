package org.javaschool;

import org.apache.commons.lang3.time.DateUtils;
import org.javaschool.dao.interfaces.TicketDao;
import org.javaschool.dto.PassengerDto;
import org.javaschool.dto.TicketDto;
import org.javaschool.dto.TrainDto;
import org.javaschool.entities.TicketEntity;
import org.javaschool.entities.TrainEntity;
import org.javaschool.mapper.TicketMapper;
import org.javaschool.mapper.TrainMapper;
import org.javaschool.services.impl.TicketServiceImpl;
import org.javaschool.services.interfaces.ScheduleService;
import org.javaschool.services.interfaces.SectionService;
import org.javaschool.services.interfaces.StationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @InjectMocks
    private TicketServiceImpl ticketService;
    @Mock
    private ScheduleService scheduleService;
    @Mock
    private SectionService sectionService;
    @Mock
    private StationService stationService;
    @Mock
    private TicketMapper ticketMapper;
    @Mock
    private TrainMapper trainMapper;
    @Mock
    private TicketDao ticketDao;

    private static final Date DATE_TODAY = new Date();
    private static final Date DATE_TOMORROW = DateUtils.addDays(new Date(), 1);
    private static final Date CURRENT_TIME_PLUS_15_MINUTES = Date.from(Instant.now().plusSeconds(900));
    private static final Date CURRENT_TIME_PLUS_5_MINUTES = Date.from(Instant.now().plusSeconds(300));

    private static TicketDto ticketDto;
    private static TicketEntity ticketEntity;

    @BeforeAll
    static void setUp() {
        ticketEntity = new TicketEntity();
        PassengerDto testPassenger = PassengerDto.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .birthDate("01.01.2001")
                .passportNumber(12345678)
                .build();
        TrainDto testTrain = TrainDto.builder()
                .id(1)
                .name("Test I")
                .capacity(3)
                .build();
        ticketDto = TicketDto.builder()
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
        ticketDto.getTrains().add(testTrain);
    }

    @Test
    public void testGenerateTicketNumber() {
        assertNotNull(ticketService.generateTicketNumber(ticketDto));
    }

    @Test
    public void testIsExpiredFalse() {
        when(scheduleService.convertStringtoDate(any())).thenReturn(CURRENT_TIME_PLUS_5_MINUTES);
        assertFalse(ticketService.isExpired(ticketDto));
    }

    @Test
    public void testIsExpiredTrue() {
        when(scheduleService.convertStringtoDate(any())).thenReturn(DateUtils.setYears(DATE_TODAY, 2015));
        assertTrue(ticketService.isExpired(ticketDto));
    }

    @Test
    public void testAreTicketsAvailable() {
        when(scheduleService.convertStringtoDate(any())).thenReturn(DATE_TODAY);
        when(sectionService.getSectionsByRoute(anyList())).thenReturn(new ArrayList<>());
        when(scheduleService.convertStringtoDate(anyString())).thenReturn(DATE_TODAY);
        when(stationService.getRoute(anyString(), anyString())).thenReturn(new LinkedList<>());

        assertTrue(ticketService.areTicketsAvailable(ticketDto));
    }

    @Test
    public void testIsPassengerNotPresentOnTrain() {
        List<TicketDto> ticketDtoList = new ArrayList<>();
        ticketDtoList.add(ticketDto);

        when(trainMapper.toEntity(any())).thenReturn(new TrainEntity());
        when(ticketDao.getTicketsByTrainAndDate(any(), any())).thenReturn(new ArrayList<>());
        when(ticketMapper.toDtoList(anyList())).thenReturn(ticketDtoList);

        assertFalse(ticketService.isPassengerNotPresentOnTrain(ticketDto));
    }

    @Test
    public void testIsEnoughTimeToDepartureOnAnotherDate() {
        ticketEntity.setDate(DATE_TOMORROW);

        when(ticketMapper.toEntity(ticketDto)).thenReturn(ticketEntity);

        assertTrue(ticketService.isEnoughTimeToDeparture(ticketDto));
    }

    @Test
    public void testIsEnoughTimeToDepartureTodayWithFifteenMinutesLeft() {
        ticketEntity.setDate(DATE_TODAY);

        when(ticketMapper.toEntity(ticketDto)).thenReturn(ticketEntity);
        when(scheduleService.convertStringtoDate(any())).thenReturn(CURRENT_TIME_PLUS_15_MINUTES);

        assertTrue(ticketService.isEnoughTimeToDeparture(ticketDto));
    }

    @Test
    public void testIsEnoughTimeToDepartureTodayWithFiveMinutesLeft() {
        ticketEntity.setDate(DATE_TODAY);

        when(ticketMapper.toEntity(ticketDto)).thenReturn(ticketEntity);
        when(scheduleService.convertStringtoDate(any())).thenReturn(CURRENT_TIME_PLUS_5_MINUTES);

        assertFalse(ticketService.isEnoughTimeToDeparture(ticketDto));
    }
}