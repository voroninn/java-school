package org.javaschool.controllers;

import lombok.RequiredArgsConstructor;
import org.javaschool.dto.PassengerDto;
import org.javaschool.dto.TicketDto;
import org.javaschool.dto.UserDto;
import org.javaschool.services.interfaces.PassengerService;
import org.javaschool.services.interfaces.SecurityService;
import org.javaschool.services.interfaces.TicketService;
import org.javaschool.services.interfaces.UserService;
import org.javaschool.validation.PassengerValidator;
import org.javaschool.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;
    private final UserValidator userValidator;
    private final PassengerValidator passengerValidator;
    private final PassengerService passengerService;
    private final TicketService ticketService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") UserDto userForm, BindingResult bindingResult) {
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
            model.addAttribute("error", "Username or password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @GetMapping(value = "/myaccount/{username}")
    public ModelAndView editPersonalData(@PathVariable("username") String username,
                                         @RequestParam(required = false) String message) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("personalEdit");
        UserDto userDto = userService.findUserByUsername(username);
        modelAndView.addObject("user", userDto);
        if (message != null) {
            modelAndView.addObject("message", message);
        }
        PassengerDto passengerDto = passengerService.getPassengerByUser(userDto);
        if (passengerDto != null) {
            modelAndView.addObject("passengerForm", passengerDto);
        } else {
            modelAndView.addObject("passengerForm", new PassengerDto());
        }
        return modelAndView;
    }

    @PostMapping(value = "/myaccount")
    public ModelAndView editPersonalData(@ModelAttribute("passengerForm") PassengerDto passengerDto,
                                         BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        UserDto userDto = userService.findUserByUsername(userService.getCurrentUserName());
        modelAndView.addObject("user", userDto);
        passengerValidator.validate(passengerDto, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("personalEdit");
        } else {
            modelAndView.setViewName("redirect:/");
            passengerDto.setUser(userDto);
            if (passengerService.getPassenger(passengerDto.getId()) != null) {
                passengerService.editPassenger(passengerDto);
            } else {
                passengerService.addPassenger(passengerDto);
            }
        }
        return modelAndView;
    }

    @GetMapping(value = "/myaccount/{username}/tickets")
    public ModelAndView viewTickets(@PathVariable("username") String username) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tickets");
        UserDto userDto = userService.findUserByUsername(username);
        PassengerDto passengerDto = passengerService.getPassengerByUser(userDto);
        List<TicketDto> tickets = ticketService.getTicketsByPassenger(passengerDto);
        modelAndView.addObject("ticketsList", tickets);
        return modelAndView;
    }
}