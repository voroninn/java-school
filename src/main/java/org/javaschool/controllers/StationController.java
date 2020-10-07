package org.javaschool.controllers;

import org.javaschool.entities.ScheduleEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.services.ScheduleService;
import org.javaschool.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;
import java.util.List;

@Controller
@SessionAttributes("ticketForm")
public class StationController {

    @Autowired
    private StationService stationService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping(value = "/")
    public ModelAndView homePage() {
        List<StationEntity> stations = stationService.getAllStations();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("stationsList", stations);
        return modelAndView;
    }

    @PostMapping(value = "/searchResult")
    public ModelAndView searchResult(@RequestParam("stationFrom") String stationFrom,
                                     @RequestParam("stationTo") String stationTo) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("searchResult");
        LinkedList<StationEntity> route = stationService.getRoute(stationFrom, stationTo);
        modelAndView.addObject("route", route);
        int numberOfChanges = 0;
        if (route.size() > 2) {
            numberOfChanges = stationService.countTrackChanges(route);
        }
        modelAndView.addObject("numberOfChanges", numberOfChanges);
        List<ScheduleEntity> schedules = scheduleService.getSchedulesByRoute(route);
        modelAndView.addObject("schedulesList", schedules);
        return modelAndView;
    }

    @GetMapping(value = "/loginSuccess")
    public ModelAndView loginSuccess() {
        List<StationEntity> stations = stationService.getAllStations();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("loginSuccess");
        modelAndView.addObject("stationsList", stations);
        return modelAndView;
    }

    @GetMapping(value = "/stations")
    public ModelAndView allStations() {
        List<StationEntity> stations = stationService.getAllStations();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stations");
        modelAndView.addObject("stationsList", stations);
        return modelAndView;
    }

    @GetMapping(value = "/edit/station/{id}")
    public ModelAndView editStation(@PathVariable("id") int id) {
        StationEntity station = stationService.getStation(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stationEdit");
        modelAndView.addObject("station", station);
        return modelAndView;
    }

    @PostMapping(value = "/edit/station")
    public ModelAndView editStation(@ModelAttribute("station") StationEntity station) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/stations");
        stationService.editStation(station);
        return modelAndView;
    }

    @GetMapping(value = "/add/station")
    public ModelAndView addStation() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stationEdit");
        modelAndView.addObject("station", new StationEntity());
        return modelAndView;
    }

    @PostMapping(value = "/add/station")
    public ModelAndView addStation(@ModelAttribute("station") StationEntity station) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/stations");
        stationService.addStation(station);
        return modelAndView;
    }

    @GetMapping(value="/delete/station/{id}")
    public ModelAndView deleteStation(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/stations");
        StationEntity station = stationService.getStation(id);
        stationService.deleteStation(station);
        return modelAndView;
    }
}
