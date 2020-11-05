package org.javaschool.controllers;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.javaschool.mapper.TimetableScheduleMapper;
import org.javaschool.services.interfaces.ScheduleService;
import org.javaschool.services.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Log4j2
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private StationService stationService;

    @Autowired
    private TimetableScheduleMapper timetableScheduleMapper;

    @GetMapping(value = "/timetable/{stationName}")
    public String generateResponse(@PathVariable String stationName) {
        log.info("Received request from 2nd app");
        return new Gson().toJson(timetableScheduleMapper.toDtoList(
                scheduleService.getSchedulesByStation(stationService.getStationByName(stationName))));
    }
}