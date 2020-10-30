package org.javaschool.controllers;

import org.javaschool.dto.ScheduleDto;
import org.javaschool.dto.StationDto;
import org.javaschool.dto.TrainDto;
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
        List<TrainDto> trainDtoList = trainService.getAllTrains();
        List<List<StationDto>> endpointsList = new ArrayList<>();
        for (TrainDto trainDto : trainDtoList) {
            List<StationDto> stationDtoList = mappingService.getOrderedStationsByTrack(trainDto.getTrack());
            if (!scheduleService.getSchedulesByTrain(trainDto).get(0).isDirection()) {
                Collections.reverse(stationDtoList);
            }
            if (!stationDtoList.isEmpty()) {
                List<StationDto> endpoints = stationService.selectEndpoints(stationDtoList);
                endpointsList.add(endpoints);
            } else {
                endpointsList.add(stationDtoList);
            }
        }
        modelAndView.addObject("endpointsList", endpointsList);
        modelAndView.addObject("trainsList", trainDtoList);
        return modelAndView;
    }

    @GetMapping(value = "/trains/edit/{id}")
    public ModelAndView editTrain(@PathVariable("id") int id) {
        TrainDto trainDto = trainService.getTrain(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trainEdit");
        modelAndView.addObject("train", trainDto);
        return modelAndView;
    }

    @PostMapping(value = "/trains/edit")
    public ModelAndView editTrain(@ModelAttribute("train") TrainDto trainDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/trains/edit/" + trainDto.getId() + "/schedule");
        modelAndView.addObject("train", trainDto);
        trainService.editTrain(trainDto);
        return modelAndView;
    }

    @GetMapping(value = "/trains/add")
    public ModelAndView addTrain() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trainEdit");
        modelAndView.addObject("train", new TrainDto());
        return modelAndView;
    }

    @PostMapping(value = "/trains/add")
    public ModelAndView addTrain(@ModelAttribute("train") TrainDto trainDto) {
        ModelAndView modelAndView = new ModelAndView();
        trainService.addTrain(trainDto);
        modelAndView.setViewName("redirect:/trains/edit/" + trainDto.getId() + "/schedule");
        modelAndView.addObject("train", trainDto);
        return modelAndView;
    }

    @GetMapping(value = "/trains/edit/{id}/schedule")
    public ModelAndView editTrainSchedule(@ModelAttribute("train") TrainDto trainDto,
                                          @RequestParam(required = false) String reverse) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trainSchedule");
        modelAndView.addObject("train", trainDto);
        List<StationDto> stationDtoList = mappingService.getOrderedStationsByTrack(trainDto.getTrack());
        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        if (!scheduleService.getSchedulesByTrain(trainDto).isEmpty()) {
            scheduleDtoList = scheduleService.getSchedulesByTrain(trainDto);
        } else {
            for (StationDto stationDto : stationDtoList) {
                scheduleDtoList.add(new ScheduleDto(stationDto, trainDto));
            }
        }
        boolean isReversed = false;
        if (reverse != null && reverse.equals("true")) {
            Collections.reverse(scheduleDtoList);
            isReversed = true;
        }
        modelAndView.addObject("isReversed", isReversed);
        modelAndView.addObject("schedulesList", scheduleDtoList);
        return modelAndView;
    }

    @PostMapping(value = "/trains/edit/schedule")
    public ModelAndView editTrainSchedule(@RequestParam Map<String, String> requestParams,
                                          @ModelAttribute("train") TrainDto trainDto) {
        ModelAndView modelAndView = new ModelAndView();
        for (int i = 0; i < requestParams.size() / 3 - 1; i++) {
            ScheduleDto scheduleDto = new ScheduleDto();
            scheduleDto.setId(Integer.parseInt(requestParams.get("id" + i)));
            scheduleDto.setStation(stationService.getStationByName(requestParams.get("station" + i)));
            scheduleDto.setTrain(trainDto);
            scheduleDto.setArrivalTime(requestParams.get("arrivalTime" + i));
            scheduleDto.setDepartureTime(requestParams.get("departureTime" + i));
            scheduleDto.setDirection(!Boolean.parseBoolean(requestParams.get("isReversed")));
            scheduleService.editSchedule(scheduleDto);
        }
        modelAndView.setViewName("redirect:/trains");
        return modelAndView;
    }

    @GetMapping(value = "/trains/delete/{id}")
    public ModelAndView deleteTrain(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/trains");
        TrainDto trainDto = trainService.getTrain(id);
        trainService.deleteTrain(trainDto);
        return modelAndView;
    }
}