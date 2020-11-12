package org.javaschool;

import org.apache.commons.lang3.time.DateUtils;
import org.javaschool.dto.TicketDto;
import org.javaschool.services.interfaces.ScheduleService;
import org.javaschool.validation.TicketValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketValidatorTest {

    @InjectMocks
    private TicketValidator ticketValidator;
    @Mock
    private ScheduleService scheduleService;

    private static TicketDto ticketDto;
    private static Errors errors;

    private static final String EMPTY_STRING = "";
    private static final String DEP_STATION_VALID = "Tatooine";
    private static final String ARR_STATION_VALID = "Dantooine";
    private static final Date DATE_TODAY = new Date();
    private final static String DATE_PAST = "18.04.1991";
    private final static String DATE_INVALID = "1.804!199-1";
    private static final String DATE_TOMORROW =
            new SimpleDateFormat("dd.MM.yyyy").format(DateUtils.addDays(new Date(), 1));

    @BeforeEach
    public void setUp() {
        ticketDto = TicketDto.builder()
                .departureStation(DEP_STATION_VALID)
                .arrivalStation(ARR_STATION_VALID)
                .date(DATE_TOMORROW)
                .build();
        errors = new BeanPropertyBindingResult(ticketDto, "ticketDto");
        lenient().when(scheduleService.convertStringtoDate(anyString())).thenReturn(DATE_TODAY);
    }

    @Test
    public void validateTicketValid() {
        ticketValidator.validate(ticketDto, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validateTicketDepartureEmpty() {
        ticketDto.setDepartureStation(EMPTY_STRING);
        ticketValidator.validate(ticketDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("departureStation"));
    }

    @Test
    public void validateTicketArrivalEmpty() {
        ticketDto.setArrivalStation(EMPTY_STRING);
        ticketValidator.validate(ticketDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("arrivalStation"));
    }

    @Test
    public void validateTicketDepartureArrivalEmpty() {
        ticketDto.setDepartureStation(EMPTY_STRING);
        ticketDto.setArrivalStation(EMPTY_STRING);
        ticketValidator.validate(ticketDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("departureStation"));
        assertNotNull(errors.getFieldError("arrivalStation"));
    }

    @Test
    public void validateTicketDepartureArrivalSame() {
        ticketDto.setArrivalStation(DEP_STATION_VALID);
        ticketValidator.validate(ticketDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("departureStation"));
        assertNotNull(errors.getFieldError("arrivalStation"));
    }

    @Test
    public void validateTicketDateEmpty() {
        ticketDto.setDate(EMPTY_STRING);
        ticketValidator.validate(ticketDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("date"));
    }

    @Test
    public void validateTicketDateInvalid() {
        ticketDto.setDate(DATE_INVALID);
        ticketValidator.validate(ticketDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("date"));
    }

    @Test
    public void validateTicketDatePast() throws ParseException {
        when(scheduleService.convertStringtoDate(anyString()))
                .thenReturn(new SimpleDateFormat("dd.MM.yyyy").parse(DATE_PAST));
        ticketValidator.validate(ticketDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("date"));
    }
}