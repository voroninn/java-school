package org.javaschool.controllers;

import org.javaschool.dto.PassengerDto;
import org.javaschool.services.interfaces.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @GetMapping(value = "/passengers")
    public ModelAndView allPassengers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("passengers");
        modelAndView.addObject("passengersList", passengerService.getAllPassengers());
        return modelAndView;
    }

    @GetMapping(value = "/passengers/{trainId}")
    public ModelAndView passengersByTrain(@PathVariable("trainId") int trainId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("passengers");
        modelAndView.addObject("passengersList", passengerService.getPassengersByTrainId(trainId));
        return modelAndView;
    }

    @GetMapping(value = "/passengers/edit/{id}")
    public ModelAndView editPassenger(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("passengerEdit");
        modelAndView.addObject("passenger", passengerService.getPassenger(id));
        return modelAndView;
    }

    @PostMapping(value = "/passengers/edit")
    public ModelAndView editPassenger(@ModelAttribute("passenger") PassengerDto passengerDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/passengers");
        passengerService.editPassenger(passengerDto);
        return modelAndView;
    }

    @GetMapping(value = "/passengers/add")
    public ModelAndView addPassenger() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("passengerEdit");
        modelAndView.addObject("passenger", new PassengerDto());
        return modelAndView;
    }

    @PostMapping(value = "/passengers/add")
    public ModelAndView addPassenger(@ModelAttribute("passenger") PassengerDto passengerDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/passengers");
        passengerService.addPassenger(passengerDto);
        return modelAndView;
    }

    @GetMapping(value = "/passengers/delete/{id}")
    public ModelAndView deletePassenger(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/passengers");
        passengerService.deletePassenger(passengerService.getPassenger(id));
        return modelAndView;
    }
}