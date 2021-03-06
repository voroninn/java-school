package org.javaschool.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.javaschool.dto.*;
import org.javaschool.services.interfaces.*;
import org.javaschool.validation.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
@SessionAttributes("ticketForm")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketController {

    private final TicketService ticketService;
    private final StationService stationService;
    private final ScheduleService scheduleService;
    private final TrainService trainService;
    private final UserService userService;
    private final PassengerService passengerService;
    private final TicketValidator ticketValidator;

    @GetMapping(value = "/")
    public ModelAndView homePage(@ModelAttribute("ticketForm") TicketDto ticketDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("stationsList", stationService.getAllStations());
        modelAndView.addObject("ticketForm", new TicketDto());
        return modelAndView;
    }

    @ModelAttribute("ticketForm")
    public TicketDto createTicket() {
        return new TicketDto();
    }

    @PostMapping(value = "/schedule")
    public ModelAndView searchResult(@ModelAttribute("ticketForm") TicketDto ticketDto, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        ticketValidator.validate(ticketDto, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("index");
            modelAndView.addObject("stationsList", stationService.getAllStations());
        } else {
            modelAndView.setViewName("searchResult");
            LinkedList<StationDto> route = stationService.getRoute(ticketDto.getDepartureStation(), ticketDto.getArrivalStation());
            modelAndView.addObject("route", route);
            modelAndView.addObject("numberOfChanges", stationService.countTrackChanges(route));
            List<ScheduleDto> scheduleDtoList = scheduleService.buildSchedule(route,
                    DateUtils.isSameDay(scheduleService.convertStringtoDate(ticketDto.getDate()), new Date())
                            ? new Date() : scheduleService.convertStringtoDate("00:00"));
            modelAndView.addObject("schedules", scheduleDtoList);
            ticketDto.setTrains(trainService.getTrainsBySchedule(scheduleDtoList));
            ticketDto.setPrice(ticketService.calculateTicketPrice(route));
        }
        return modelAndView;
    }

    @PostMapping(value = "/ticket/verify")
    public ModelAndView ticketPage(@ModelAttribute("ticketForm") TicketDto ticketDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ticketPage");
        UserDto userDto = userService.findUserByUsername(userService.getCurrentUserName());
        PassengerDto passengerDto = passengerService.getPassengerByUser(userDto);
            if (passengerDto != null ) {
                modelAndView.addObject("passenger", passengerDto);
                ticketDto.setPassenger(passengerDto);
            } else {
                modelAndView.addObject("message", "Please enter your personal data");
            }
        modelAndView.addObject("trainsList", new ArrayList<>(ticketDto.getTrains()));
        return modelAndView;
    }

    @GetMapping(value = "/ticket/buy")
    public ModelAndView ticketBuy(@ModelAttribute("ticketForm") TicketDto ticketDto) {
        ModelAndView modelAndView = new ModelAndView();
        List<String> validationMessages = ticketService.validateTicket(ticketDto);
        if (validationMessages.get(0).equals("success")) {
            modelAndView.setViewName("ticketBuy");
            modelAndView.addObject("passenger", ticketDto.getPassenger());
            modelAndView.addObject("trainsList", new ArrayList<>(ticketDto.getTrains()));
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
        ticketService.deleteTicket(ticketService.getTicket(id));
        return modelAndView;
    }
}