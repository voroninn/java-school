package org.javaschool.controllers;

import org.javaschool.entities.PassengerEntity;
import org.javaschool.entities.TicketEntity;
import org.javaschool.entities.UserEntity;
import org.javaschool.services.interfaces.PassengerService;
import org.javaschool.services.interfaces.SecurityService;
import org.javaschool.services.interfaces.TicketService;
import org.javaschool.services.interfaces.UserService;
import org.javaschool.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserEntity());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") UserEntity userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @GetMapping(value = "/myaccount/{username}")
    public ModelAndView editPersonalData(@PathVariable("username") String username) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("personalEdit");
        UserEntity user = userService.findUserByUsername(username);
        modelAndView.addObject("user", user);
        PassengerEntity passenger = passengerService.getPassengerByUser(user);
        if (passenger != null) {
            modelAndView.addObject("passengerForm", passenger);
        } else {
            modelAndView.addObject("passengerForm", new PassengerEntity());
        }
        return modelAndView;
    }

    @PostMapping(value = "/myaccount")
    public ModelAndView editPersonalData(@ModelAttribute("passenger") PassengerEntity passenger) {
        ModelAndView modelAndView = new ModelAndView();
        UserEntity user = userService.findUserByUsername(userService.getCurrentUserName());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("redirect:/myaccount/" + user.getUsername());
        passenger.setUser(user);
        if (passengerService.getPassenger(passenger.getId()) != null) {
            passengerService.editPassenger(passenger);
        } else {
            passengerService.addPassenger(passenger);
        }
        return modelAndView;
    }

    @GetMapping(value = "/myaccount/{username}/tickets")
    public ModelAndView viewTickets(@PathVariable("username") String username) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tickets");
        UserEntity user = userService.findUserByUsername(username);
        PassengerEntity passenger = passengerService.getPassengerByUser(user);
        List<TicketEntity> tickets = ticketService.getTicketsByPassenger(passenger);
        modelAndView.addObject("ticketsList", tickets);
        return modelAndView;
    }
}