package org.javaschool.controllers;

import org.javaschool.entities.PassengerEntity;
import org.javaschool.services.interfaces.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @GetMapping(value = "/passengers")
    public ModelAndView allPassengers() {
        List<PassengerEntity> passengers = passengerService.getAllPassengers();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("passengers");
        modelAndView.addObject("passengersList", passengers);
        return modelAndView;
    }

    @GetMapping(value = "/passengers/{trainId}")
    public ModelAndView passengersByTrain(@PathVariable("trainId") int trainId) {
        List<PassengerEntity> passengers = passengerService.getPassengersByTrainId(trainId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("passengers");
        modelAndView.addObject("passengersList", passengers);
        return modelAndView;
    }

    @GetMapping(value = "/passengers/edit/{id}")
    public ModelAndView editPassenger(@PathVariable("id") int id) {
        PassengerEntity passenger = passengerService.getPassenger(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("passengerEdit");
        modelAndView.addObject("passenger", passenger);
        return modelAndView;
    }

    @PostMapping(value = "/passengers/edit")
    public ModelAndView editPassenger(@ModelAttribute("passenger") PassengerEntity passenger) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/passengers");
        passengerService.editPassenger(passenger);
        return modelAndView;
    }

    @GetMapping(value = "/passengers/add")
    public ModelAndView addPassenger() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("passengerEdit");
        modelAndView.addObject("passenger", new PassengerEntity());
        return modelAndView;
    }

    @PostMapping(value = "/passengers/add")
    public ModelAndView addPassenger(@ModelAttribute("passenger") PassengerEntity passenger) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/passengers");
        passengerService.addPassenger(passenger);
        return modelAndView;
    }

    @GetMapping(value = "/passengers/delete/{id}")
    public ModelAndView deletePassenger(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/passengers");
        PassengerEntity passenger = passengerService.getPassenger(id);
        passengerService.deletePassenger(passenger);
        return modelAndView;
    }
}