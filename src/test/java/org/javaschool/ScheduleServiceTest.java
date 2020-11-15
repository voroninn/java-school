package org.javaschool;

import org.apache.commons.lang3.time.DateUtils;
import org.javaschool.dto.ScheduleDto;
import org.javaschool.services.impl.ScheduleServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    private static final String TIME_NOON = "12:00";
    private static final String TIME_EVENING = "20:00";
    private static final String TIME_NIGHT = "03:00";
    private static final String DATE = "11.11.2020";
    private static final String DATE_INVALID = "11112020";
    private static final String TIME_FORMAT = "HH:mm";

    @Disabled
    @Test
    void testOrderSchedulesByTime() {
        List<ScheduleDto> scheduleDtoList = new ArrayList<>();

        scheduleDtoList.add(new ScheduleDto(TIME_NIGHT));
        scheduleDtoList.add(new ScheduleDto(TIME_EVENING));
        scheduleDtoList.add(new ScheduleDto(TIME_NOON));

        scheduleDtoList = scheduleService.orderSchedulesByTime(scheduleDtoList);

        assertTrue(scheduleDtoList.get(0).getDepartureTime().equals(TIME_NOON) &&
                scheduleDtoList.get(2).getDepartureTime().equals(TIME_NIGHT));
    }

    @Test
    void testConvertStringtoTime() {
        assertNotNull(scheduleService.convertStringtoDate(TIME_NOON));
    }

    @Test
    void testConvertStringtoDate() {
        assertNotNull(scheduleService.convertStringtoDate(DATE));
    }

    @Test
    void testConvertInvalidStringtoDate() {
        assertNull(scheduleService.convertStringtoDate(DATE_INVALID));
    }

    @Test
    void testIsTimeBefore() throws ParseException {
        assertTrue(scheduleService.isTimeBefore(new SimpleDateFormat((TIME_FORMAT)).parse(TIME_NOON),
                DateUtils.addHours(new SimpleDateFormat(TIME_FORMAT).parse(TIME_NOON), 3)));
    }
}