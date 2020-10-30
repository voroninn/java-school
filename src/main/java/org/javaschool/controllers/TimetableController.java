package org.javaschool.controllers;

import org.javaschool.dto.StationDto;
import org.javaschool.services.interfaces.ScheduleService;
import org.javaschool.services.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TimetableController {

    @Autowired
    private StationService stationService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping(value = "/timetable")
    public ModelAndView timetable() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("timetable");
        List<StationDto> stationDtoList = stationService.getAllStations();
        modelAndView.addObject("stationsList", stationDtoList);
        modelAndView.addObject("schedules", scheduleService.getAllSchedulesByStations(stationDtoList));
        return modelAndView;
    }
}