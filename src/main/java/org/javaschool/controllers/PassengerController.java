package org.javaschool.controllers;

import org.javaschool.entities.PassengerEntity;
import org.javaschool.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @GetMapping(value = "/")
    public ModelAndView homePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping(value = "/passengers")
    public ModelAndView allPassengers() {
        List<PassengerEntity> passengers = passengerService.getAllPassengers();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("passengers");
        modelAndView.addObject("passengersList", passengers);
        return modelAndView;
    }

    @GetMapping(value = "/edit/passenger/{id}")
    public ModelAndView passengerEdit(@PathVariable("id") int id) {
        PassengerEntity passenger = passengerService.getPassenger(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("passengerEdit");
        modelAndView.addObject("passenger", passenger);
        return modelAndView;
    }

    @PostMapping(value = "/edit/passenger")
    public ModelAndView editPassenger(@ModelAttribute("passenger") PassengerEntity passenger) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/passengers");
        passengerService.updatePassenger(passenger);
        return modelAndView;
    }

    @GetMapping(value = "/add/passenger")
    public ModelAndView passengerAdd() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("passengerEdit");
        modelAndView.addObject("passenger", new PassengerEntity());
        return modelAndView;
    }

    @PostMapping(value = "/add/passenger")
    public ModelAndView addPassenger(@ModelAttribute("passenger") PassengerEntity passenger) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/passengers");
        passengerService.addPassenger(passenger);
        return modelAndView;
    }

    @GetMapping(value="/delete/passenger/{id}")
    public ModelAndView deletePassenger(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/passengers");
        PassengerEntity passenger = passengerService.getPassenger(id);
        passengerService.deletePassenger(passenger);
        return modelAndView;
    }
}
