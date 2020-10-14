package org.javaschool.controllers;

import org.javaschool.entities.StationEntity;
import org.javaschool.services.interfaces.MappingService;
import org.javaschool.services.interfaces.ScheduleService;
import org.javaschool.services.interfaces.SectionService;
import org.javaschool.services.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("station")
public class StationController {

    @Autowired
    private StationService stationService;

    @Autowired
    private MappingService mappingService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private ScheduleService scheduleService;

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
    public ModelAndView editStation(@ModelAttribute("station") StationEntity station, @RequestParam String name) {
        ModelAndView modelAndView = new ModelAndView();
        station.setName("name");
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
    public ModelAndView addStation(@ModelAttribute("station") StationEntity station,
                                   @RequestParam Map<String, String> requestParams, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        station.setName(requestParams.get("name"));
        redirectAttributes.addAttribute("track", requestParams.get("track"));
        redirectAttributes.addAttribute("length", requestParams.get("length"));
        modelAndView.setViewName("redirect:/stations/edit/" + station.getId() + "/track");
        return modelAndView;
    }

    @GetMapping(value = "/stations/edit/{id}/track")
    public ModelAndView editTrack(@ModelAttribute("station") StationEntity station,
                                          @RequestParam Map<String, String> requestParams) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stationMapping");
        List<StationEntity> stations = mappingService.
                getOrderedStationsByTrack(mappingService.getTrack(Integer.parseInt(requestParams.get("track"))));
        modelAndView.addObject("stationsList", stations);
        modelAndView.addObject("track", requestParams.get("track"));
        modelAndView.addObject("length", requestParams.get("length"));
        return modelAndView;
    }

    @PostMapping(value = "/stations/edit/track")
    public ModelAndView editTrack(@RequestParam Map<String, String> requestParams,
                                          @ModelAttribute("station") StationEntity station) {
        ModelAndView modelAndView = new ModelAndView();
        mappingService.appendStation(station, Integer.parseInt(requestParams.get("track")),
                requestParams.get("appendLocation"));
        sectionService.createSection(station, Integer.parseInt(requestParams.get("length")),
                mappingService.getTrack(Integer.parseInt(requestParams.get("track"))));
        scheduleService.createEmptyScheduleForStation(station, Integer.parseInt(requestParams.get("track")));
        modelAndView.setViewName("redirect:/stations");
        return modelAndView;
    }

    @GetMapping(value = "/stations/delete/{id}")
    public ModelAndView deleteStation(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/stations");
        StationEntity station = stationService.getStation(id);
        stationService.deleteStation(station);
        return modelAndView;
    }
}