package org.javaschool.controllers;

import org.javaschool.entities.ScheduleEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.services.interfaces.ScheduleService;
import org.javaschool.services.interfaces.StationService;
import org.javaschool.services.interfaces.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;
import java.util.List;

@Controller
@SessionAttributes("ticketForm")
public class TicketController {

    @Autowired
    private StationService stationService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TicketService ticketService;

    @GetMapping(value = "/")
    public ModelAndView homePage() {
        List<StationEntity> stations = stationService.getAllStations();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("stationsList", stations);
        return modelAndView;
    }

    @PostMapping(value = "/searchResult")
    public ModelAndView searchResult(@RequestParam("stationFrom") String stationFrom,
                                     @RequestParam("stationTo") String stationTo) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("searchResult");
        LinkedList<StationEntity> route = stationService.getRoute(stationFrom, stationTo);
        modelAndView.addObject("route", route);
        int numberOfChanges = 0;
        if (route.size() > 2) {
            numberOfChanges = stationService.countTrackChanges(route);
        }
        modelAndView.addObject("numberOfChanges", numberOfChanges);
        List<ScheduleEntity> schedules = scheduleService.getSchedulesByRoute(route);
        modelAndView.addObject("schedulesList", schedules);
        return modelAndView;
    }

}
