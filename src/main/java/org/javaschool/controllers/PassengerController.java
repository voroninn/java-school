package org.javaschool.controllers;

import org.javaschool.dto.PassengerDto;
import org.javaschool.services.interfaces.PassengerService;
import org.javaschool.validation.PassengerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private PassengerValidator passengerValidator;

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
        modelAndView.addObject("passengerForm", passengerService.getPassenger(id));
        return modelAndView;
    }

    @PostMapping(value = "/passengers/edit")
    public ModelAndView editPassenger(@ModelAttribute("passengerForm") PassengerDto passengerDto,
                                      BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        passengerValidator.validate(passengerDto, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("passengerEdit");
        } else {
            modelAndView.setViewName("redirect:/passengers");
            passengerService.editPassenger(passengerDto);
        }
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