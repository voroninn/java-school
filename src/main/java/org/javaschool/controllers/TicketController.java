package org.javaschool.controllers;

import org.javaschool.entities.ScheduleEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TicketEntity;
import org.javaschool.services.interfaces.ScheduleService;
import org.javaschool.services.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
@SessionAttributes("ticketForm")
public class TicketController {

    @Autowired
    private StationService stationService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping(value = "/")
    public ModelAndView homePage(@ModelAttribute("ticketForm") TicketEntity ticket) {
        List<StationEntity> stations = stationService.getAllStations();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("stationsList", stations);
        modelAndView.addObject("ticketForm", new TicketEntity());
        return modelAndView;
    }

    @ModelAttribute("ticketForm")
    public TicketEntity createTicket() {
        return new TicketEntity();
    }

    @PostMapping(value = "/searchResult")
    public ModelAndView searchResult(@ModelAttribute("ticketForm") TicketEntity ticket) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("searchResult");

        LinkedList<StationEntity> route = stationService.getRoute(ticket.getDepartureStation(), ticket.getArrivalStation());
        modelAndView.addObject("route", route);

        int numberOfChanges = 0;
        if (route.size() > 2) {
            numberOfChanges = stationService.countTrackChanges(route);
        }
        modelAndView.addObject("numberOfChanges", numberOfChanges);

        List<ScheduleEntity> schedules = scheduleService.getSchedulesByRoute(route);
        scheduleService.putSchedulesInCorrectOrder(schedules);
        if (schedules.size() > route.size()) {
            List<List<ScheduleEntity>> separatedSchedules = scheduleService.separateSchedules(schedules, route.size());
            modelAndView.addObject("separatedSchedulesList", separatedSchedules);
        } else {
            modelAndView.addObject("schedulesList", schedules);
        }
        modelAndView.addObject("ticketForm", ticket);
        return modelAndView;
    }

    @PostMapping(value = "/ticketPage")
    public ModelAndView ticketPage(@ModelAttribute("ticketForm") TicketEntity ticket) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ticketPage");
        return modelAndView;
    }
}
