package org.javaschool.controllers;

import org.javaschool.entities.PassengerEntity;
import org.javaschool.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    @GetMapping(value = "/edit/{id}")
    public ModelAndView editPage(@PathVariable("id") int id) {
        PassengerEntity passenger = passengerService.getPassenger(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPage");
        modelAndView.addObject("passenger", passenger);
        //modelAndView.addObject("localDateTimeFormat", new SimpleDateFormat("dd.MM.yyyy"));
        return modelAndView;
    }

    @PostMapping(value = "/edit")
    public ModelAndView editPassenger(@ModelAttribute("passenger") PassengerEntity passenger) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/passengers");
        passengerService.updatePassenger(passenger);
        return modelAndView;
    }

    @GetMapping(value = "/add")
    public ModelAndView addPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPage");
        return modelAndView;
    }

    @PostMapping(value = "/add")
    public ModelAndView addPassenger(@ModelAttribute("passenger") PassengerEntity passenger) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/passengers");
        passengerService.addPassenger(passenger);
        return modelAndView;
    }

    @GetMapping(value="/delete/{id}")
    public ModelAndView deletePassenger(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/passengers");
        PassengerEntity passenger = passengerService.getPassenger(id);
        passengerService.deletePassenger(passenger);
        return modelAndView;
    }

//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
//        sdf.setLenient(true);
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
//    }
}
