package org.javaschool.controllers;

import org.javaschool.entities.StationEntity;
import org.javaschool.services.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping(value = "/stations")
    public ModelAndView allStations() {
        List<StationEntity> stations = stationService.getAllStations();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stations");
        modelAndView.addObject("stationsList", stations);
        return modelAndView;
    }

    @GetMapping(value = "/stations/edit/{id}")
    public ModelAndView editStation(@PathVariable("id") int id) {
        StationEntity station = stationService.getStation(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stationEdit");
        modelAndView.addObject("station", station);
        return modelAndView;
    }

    @PostMapping(value = "/stations/edit")
    public ModelAndView editStation(@ModelAttribute("station") StationEntity station) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/stations");
        stationService.editStation(station);
        return modelAndView;
    }

    @GetMapping(value = "/stations/add")
    public ModelAndView addStation() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stationEdit");
        modelAndView.addObject("station", new StationEntity());
        return modelAndView;
    }

    @PostMapping(value = "/stations/add")
    public ModelAndView addStation(@ModelAttribute("station") StationEntity station) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/stations");
        stationService.addStation(station);
        return modelAndView;
    }

    @GetMapping(value="/stations/delete/{id}")
    public ModelAndView deleteStation(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/stations");
        StationEntity station = stationService.getStation(id);
        stationService.deleteStation(station);
        return modelAndView;
    }
}
