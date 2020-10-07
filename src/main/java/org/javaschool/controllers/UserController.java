package org.javaschool.controllers;

import org.javaschool.entities.StationEntity;
import org.javaschool.entities.UserEntity;
import org.javaschool.services.interfaces.SecurityService;
import org.javaschool.services.interfaces.StationService;
import org.javaschool.services.interfaces.UserService;
import org.javaschool.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private StationService stationService;

    @Autowired
    private UserValidator userValidator;

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
        return "redirect:/loginSuccess";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @GetMapping(value = "/loginSuccess")
    public ModelAndView loginSuccess() {
        List<StationEntity> stations = stationService.getAllStations();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("loginSuccess");
        modelAndView.addObject("stationsList", stations);
        return modelAndView;
    }
}
