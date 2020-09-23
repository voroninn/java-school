package org.javaschool.controllers;

import org.javaschool.entities.PassengerEntity;
import org.javaschool.entities.TrainEntity;
import org.javaschool.services.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TrainController {

    @Autowired
    private TrainService trainService;

    @GetMapping(value = "/trains")
    public ModelAndView allTrains() {
        List<TrainEntity> trains = trainService.getAllTrains();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trains");
        modelAndView.addObject("trainsList", trains);
        return modelAndView;
    }

    @GetMapping(value = "/edit/train/{id}")
    public ModelAndView trainEdit(@PathVariable("id") int id) {
        TrainEntity train = trainService.getTrain(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trainEdit");
        modelAndView.addObject("train", train);
        return modelAndView;
    }

    @PostMapping(value = "/edit/train")
    public ModelAndView editTrain(@ModelAttribute("train") TrainEntity train) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/trains");
        trainService.updateTrain(train);
        return modelAndView;
    }

    @GetMapping(value = "/add/train")
    public ModelAndView trainAdd() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trainEdit");
        modelAndView.addObject("train", new TrainEntity());
        return modelAndView;
    }

    @PostMapping(value = "/add/train")
    public ModelAndView addTrain(@ModelAttribute("train") TrainEntity train) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/trains");
        trainService.addTrain(train);
        return modelAndView;
    }

    @GetMapping(value="/delete/train/{id}")
    public ModelAndView deleteTrain(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/trains");
        TrainEntity train = trainService.getTrain(id);
        trainService.deleteTrain(train);
        return modelAndView;
    }
}
