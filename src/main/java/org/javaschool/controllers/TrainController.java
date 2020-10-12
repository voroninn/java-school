package org.javaschool.controllers;

import org.javaschool.entities.ScheduleEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TrainEntity;
import org.javaschool.services.interfaces.MappingService;
import org.javaschool.services.interfaces.ScheduleService;
import org.javaschool.services.interfaces.StationService;
import org.javaschool.services.interfaces.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("train")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private StationService stationService;

    @Autowired
    private MappingService mappingService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping(value = "/trains")
    public ModelAndView allTrains() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trains");
        List<TrainEntity> trains = trainService.getAllTrains();
        List<List<StationEntity>> endpointsList = new ArrayList<>();
        for (TrainEntity train : trains) {
            List<StationEntity> stations = stationService.getStationsByTrain(train);
            if (!stations.isEmpty()) {
                List<StationEntity> endpoints = stationService.selectEndpoints(stations);
                endpointsList.add(endpoints);
            } else {
                endpointsList.add(stations);
            }
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
        modelAndView.setViewName("redirect:/trains/edit/" + train.getId() + "/schedule");
        modelAndView.addObject("train", train);
        trainService.editTrain(train);
        return modelAndView;
    }

    @GetMapping(value = "/trains/edit/{id}/schedule")
    public ModelAndView editTrainSchedule(@ModelAttribute("train") TrainEntity train,
                                          @RequestParam(required = false) String reverse) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trainSchedule");
        modelAndView.addObject("train", train);
        List<StationEntity> stations = mappingService.getOrderedStationsByTrack(train.getTrack());
        List<ScheduleEntity> schedules = new ArrayList<>();
        if (!scheduleService.getSchedulesByTrain(train).isEmpty()) {
            schedules = scheduleService.getSchedulesByTrain(train);
        } else {
            for (StationEntity station : stations) {
                schedules.add(new ScheduleEntity(station, train));
            }
        }
        boolean isReversed = false;
        if (reverse != null && reverse.equals("true")) {
            Collections.reverse(schedules);
            isReversed = true;
        }
        modelAndView.addObject("isReversed", isReversed);
        modelAndView.addObject("schedulesList", schedules);
        return modelAndView;
    }

    @PostMapping(value = "/trains/edit/schedule")
    public ModelAndView editTrainSchedule(@RequestParam Map<String, String> requestParams,
                                          @ModelAttribute("train") TrainEntity train) {
        ModelAndView modelAndView = new ModelAndView();
        for (int i = 0; i < requestParams.size() / 3 - 1; i++) {
            ScheduleEntity schedule = new ScheduleEntity();
            schedule.setId(Integer.parseInt(requestParams.get("id" + i)));
            schedule.setStation(stationService.getStationByName(requestParams.get("station" + i)));
            schedule.setTrain(train);
            schedule.setArrivalTime(scheduleService.convertStringtoDate(requestParams.get("arrivalTime" + i)));
            schedule.setDepartureTime(scheduleService.convertStringtoDate(requestParams.get("departureTime" + i)));
            schedule.setDirection(!Boolean.parseBoolean(requestParams.get("isReversed")));
            scheduleService.editSchedule(schedule);
        }
        modelAndView.setViewName("redirect:/trains");
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
        trainService.addTrain(train);
        modelAndView.setViewName("redirect:/trains/edit/" + train.getId() + "/schedule");
        modelAndView.addObject("train", train);
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
