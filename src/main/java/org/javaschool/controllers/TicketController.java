package org.javaschool.controllers;

import org.javaschool.entities.*;
import org.javaschool.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Controller
@SessionAttributes("ticketForm")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private StationService stationService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private UserService userService;

    @Autowired
    private PassengerService passengerService;

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

    @PostMapping(value = "/schedule")
    public ModelAndView searchResult(@ModelAttribute("ticketForm") TicketEntity ticket) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("searchResult");
        LinkedList<StationEntity> route = stationService.getRoute(ticket.getDepartureStation(), ticket.getArrivalStation());
        modelAndView.addObject("route", route);
        stationService.setEndpoints(route);

        int numberOfChanges = 0;
        if (route.size() > 2) {
            numberOfChanges = stationService.countTrackChanges(route);
        }
        modelAndView.addObject("numberOfChanges", numberOfChanges);

        List<ScheduleEntity> schedule = scheduleService.buildSchedule(route, scheduleService.convertStringtoDate("00:00"));
        modelAndView.addObject("schedule", schedule);

        Set<TrainEntity> trains = trainService.getTrainsBySchedule(schedule);
        ticket.setPrice(ticketService.calculateTicketPrice(route));
        ticket.setTrains(trains);

        return modelAndView;
    }

    @PostMapping(value = "/ticket/verify")
    public ModelAndView ticketPage(@ModelAttribute("ticketForm") TicketEntity ticket) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ticketPage");
        UserEntity user = userService.findUserByUsername(userService.getCurrentUserName());
        PassengerEntity passenger = passengerService.getPassengerByUser(user);
        if (passenger != null) {
            if (passenger.getFirstName() != null && passenger.getLastName() != null
                    && passenger.getBirthDate() != null && passenger.getPassportNumber() != 0) {
                modelAndView.addObject("passenger", passenger);
                ticket.setPassenger(passenger);
            } else {
                modelAndView.addObject("message", "Please enter your personal data");
            }
        }
        List<TrainEntity> trains = new ArrayList<>(ticket.getTrains());
        modelAndView.addObject("trainsList", trains);
        return modelAndView;
    }

    @GetMapping(value = "/ticket/buy")
    public ModelAndView ticketBuy(@ModelAttribute("ticketForm") TicketEntity ticket) {
        ModelAndView modelAndView = new ModelAndView();
        List<String> validationMessages = ticketService.validateTicket(ticket);
        if (validationMessages.get(0).equals("success")) {
            modelAndView.setViewName("ticketBuy");
            List<TrainEntity> trains = new ArrayList<>(ticket.getTrains());
            modelAndView.addObject("passenger", ticket.getPassenger());
            modelAndView.addObject("trainsList", trains);
            ticket.setNumber(ticketService.generateTicketNumber(ticket));
            ticketService.addTicket(ticket);
        } else {
            modelAndView.setViewName("ticketBuyFailed");
            modelAndView.addObject("validationMessages", validationMessages);
        }
        return modelAndView;
    }

    @GetMapping(value = "/ticket/delete/{id}")
    public ModelAndView deleteTicket(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        UserEntity user = userService.findUserByUsername(userService.getCurrentUserName());
        modelAndView.setViewName("redirect:/myaccount/" + user.getUsername() + "/tickets");
        TicketEntity ticket = ticketService.getTicket(id);
        ticketService.deleteTicket(ticket);
        return modelAndView;
    }
}