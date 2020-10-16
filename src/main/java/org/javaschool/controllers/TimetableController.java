package org.javaschool.controllers;

import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TicketEntity;
import org.javaschool.services.interfaces.ScheduleService;
import org.javaschool.services.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TimetableController {

    @Autowired
    private StationService stationService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping(value = "/timetable")
    public ModelAndView timetable(@ModelAttribute("ticketForm") TicketEntity ticket) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("timetable");
        List<StationEntity> stations = stationService.getAllStations();
        modelAndView.addObject("stationsList", stations);
        modelAndView.addObject("schedules", scheduleService.getAllSchedulesByStations(stations));
        return modelAndView;
    }
}