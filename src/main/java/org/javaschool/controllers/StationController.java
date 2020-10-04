package org.javaschool.controllers;

import org.javaschool.entities.SectionEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.services.PathFinderService;
import org.javaschool.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@Controller
public class StationController {

    @Autowired
    private StationService stationService;

    @Autowired
    private PathFinderService pathFinder;

    @GetMapping(value = "/")
    public ModelAndView homePage() {
        List<StationEntity> stations = stationService.getAllStations();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("stationsList", stations);
        return modelAndView;
    }

    @PostMapping(value = "/searchResult")
    public ModelAndView searchResult(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("searchResult");
        System.out.println("From: " + request.getParameter("stationFrom")
                + " To: " + request.getParameter("stationTo"));
        pathFinder.initialize(stationService.getStationByName(request.getParameter("stationFrom")));
        LinkedList<StationEntity> route =
                pathFinder.createRoute(stationService.getStationByName(request.getParameter("stationTo")));
        for (StationEntity station : route) {
            System.out.println(station);
        }
        modelAndView.addObject("route", route);
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
