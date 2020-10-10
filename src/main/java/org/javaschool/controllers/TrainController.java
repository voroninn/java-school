package org.javaschool.controllers;

import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TrainEntity;
import org.javaschool.services.interfaces.StationService;
import org.javaschool.services.interfaces.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TrainController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private StationService stationService;

    @GetMapping(value = "/trains")
    public ModelAndView allTrains() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trains");
        List<TrainEntity> trains = trainService.getAllTrains();
        List<StationEntity> endpointsList = new ArrayList<>();
        for (TrainEntity train : trains) {
            List<StationEntity> stations = stationService.getStationsByTrain(train);
            List<StationEntity> endpoints = stationService.selectEndpoints(stations);
            endpointsList.addAll(endpoints);
        }
        modelAndView.addObject("endpointsList", endpointsList);
        modelAndView.addObject("trainsList", trains);
        return modelAndView;
    }

    @GetMapping(value = "/trains/edit/{id}")
    public ModelAndView editTrain(@PathVariable("id") int id) {
        TrainEntity train = trainService.getTrain(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trainEdit");
        modelAndView.addObject("train", train);
        return modelAndView;
    }

    @PostMapping(value = "/trains/edit")
    public ModelAndView editTrain(@ModelAttribute("train") TrainEntity train) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/trains");
        trainService.editTrain(train);
        return modelAndView;
    }

    @GetMapping(value = "/trains/add")
    public ModelAndView addTrain() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trainEdit");
        modelAndView.addObject("train", new TrainEntity());
        return modelAndView;
    }

    @PostMapping(value = "/trains/add")
    public ModelAndView addTrain(@ModelAttribute("train") TrainEntity train) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/trains");
        trainService.addTrain(train);
        return modelAndView;
    }

    @GetMapping(value="/trains/delete/{id}")
    public ModelAndView deleteTrain(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/trains");
        TrainEntity train = trainService.getTrain(id);
        trainService.deleteTrain(train);
        return modelAndView;
    }
}
