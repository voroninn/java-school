package org.javaschool.controllers;

import org.javaschool.dto.*;
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
    public ModelAndView homePage(@ModelAttribute("ticketForm") TicketDto ticketDto) {
        List<StationDto> stationDtoList = stationService.getAllStations();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("stationsList", stationDtoList);
        modelAndView.addObject("ticketForm", new TicketDto());
        return modelAndView;
    }

    @ModelAttribute("ticketForm")
    public TicketDto createTicket() {
        return new TicketDto();
    }

    @PostMapping(value = "/schedule")
    public ModelAndView searchResult(@ModelAttribute("ticketForm") TicketDto ticketDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("searchResult");
        LinkedList<StationDto> route = stationService.getRoute(ticketDto.getDepartureStation(), ticketDto.getArrivalStation());
        modelAndView.addObject("route", route);
        stationService.setEndpoints(route);

        int numberOfChanges = 0;
        if (route.size() > 2) {
            numberOfChanges = stationService.countTrackChanges(route);
        }
        modelAndView.addObject("numberOfChanges", numberOfChanges);

        List<ScheduleDto> schedule = scheduleService.buildSchedule(route, scheduleService.convertStringtoDate("00:00"));
        modelAndView.addObject("schedule", schedule);

        Set<TrainDto> trainDtoSet = trainService.getTrainsBySchedule(schedule);
        ticketDto.setPrice(ticketService.calculateTicketPrice(route));
        ticketDto.setTrains(trainDtoSet);

        return modelAndView;
    }

    @PostMapping(value = "/ticket/verify")
    public ModelAndView ticketPage(@ModelAttribute("ticketForm") TicketDto ticketDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ticketPage");
        UserDto userDto = userService.findUserByUsername(userService.getCurrentUserName());
        PassengerDto passengerDto = passengerService.getPassengerByUser(userDto);
        if (passengerDto != null) {
            if (passengerDto.getFirstName() != null && passengerDto.getLastName() != null
                    && passengerDto.getBirthDate() != null && passengerDto.getPassportNumber() != 0) {
                modelAndView.addObject("passenger", passengerDto);
                ticketDto.setPassenger(passengerDto);
            } else {
                modelAndView.addObject("message", "Please enter your personal data");
            }
        }
        List<TrainDto> trainDtoList = new ArrayList<>(ticketDto.getTrains());
        modelAndView.addObject("trainsList", trainDtoList);
        return modelAndView;
    }

    @GetMapping(value = "/ticket/buy")
    public ModelAndView ticketBuy(@ModelAttribute("ticketForm") TicketDto ticketDto) {
        ModelAndView modelAndView = new ModelAndView();
        List<String> validationMessages = ticketService.validateTicket(ticketDto);
        if (validationMessages.get(0).equals("success")) {
            modelAndView.setViewName("ticketBuy");
            List<TrainDto> trainDtoList = new ArrayList<>(ticketDto.getTrains());
            modelAndView.addObject("passenger", ticketDto.getPassenger());
            modelAndView.addObject("trainsList", trainDtoList);
            ticketDto.setNumber(ticketService.generateTicketNumber(ticketDto));
            ticketService.addTicket(ticketDto);
        } else {
            modelAndView.setViewName("ticketBuyFailed");
            modelAndView.addObject("validationMessages", validationMessages);
        }
        return modelAndView;
    }

    @GetMapping(value = "/ticket/delete/{id}")
    public ModelAndView deleteTicket(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        UserDto userDto = userService.findUserByUsername(userService.getCurrentUserName());
        modelAndView.setViewName("redirect:/myaccount/" + userDto.getUsername() + "/tickets");
        TicketDto ticketDto = ticketService.getTicket(id);
        ticketService.deleteTicket(ticketDto);
        return modelAndView;
    }
}