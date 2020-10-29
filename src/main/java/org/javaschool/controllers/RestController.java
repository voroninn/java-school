package org.javaschool.controllers;

import org.javaschool.entities.ScheduleEntity;
import org.javaschool.services.interfaces.ScheduleService;
import org.javaschool.services.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private StationService stationService;

    @GetMapping(value = "/timetable/{stationName}")
    public List<ScheduleEntity> getSchedulesByStation(@PathVariable String stationName) {
        return scheduleService.getSchedulesByStation(stationService.getStationByName(stationName));
    }
}
