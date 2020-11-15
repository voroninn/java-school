package org.javaschool.controllers;

import lombok.RequiredArgsConstructor;
import org.javaschool.dto.ScheduleDto;
import org.javaschool.dto.StationDto;
import org.javaschool.dto.TrainDto;
import org.javaschool.services.interfaces.*;
import org.javaschool.validation.TrainValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("train")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TrainController {

    private final TrainService trainService;
    private final TrainValidator trainValidator;
    private final StationService stationService;
    private final MappingService mappingService;
    private final ScheduleService scheduleService;
    private final MessagingService messagingService;

    @GetMapping(value = "/trains")
    public ModelAndView allTrains() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trains");
        List<TrainDto> trainDtoList = trainService.getAllTrains();
        List<List<StationDto>> endpointsList = new ArrayList<>();
        for (TrainDto trainDto : trainDtoList) {
            List<StationDto> stationDtoList = mappingService.getOrderedStationsByTrack(trainDto.getTrack());
            List<ScheduleDto> scheduleDtoList = scheduleService.getSchedulesByTrain(trainDto);
            if (!scheduleDtoList.isEmpty() && !scheduleDtoList.get(0).isDirection()) {
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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trainEdit");
        modelAndView.addObject("train", trainService.getTrain(id));
        return modelAndView;
    }

    @PostMapping(value = "/trains/edit")
    public ModelAndView editTrain(@ModelAttribute("train") TrainDto trainDto,
                                  BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        trainValidator.validate(trainDto, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("trainEdit");
            trainDto.setName("");
        } else {
            modelAndView.setViewName("redirect:/trains/edit/" + trainDto.getId() + "/schedule");
            trainService.editTrain(trainDto);
        }
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
    public ModelAndView addTrain(@ModelAttribute("train") TrainDto trainDto,
                                 BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        trainValidator.validate(trainDto, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("trainEdit");
            trainDto.setName("");
        } else {
            modelAndView.setViewName("redirect:/trains/edit/" + trainDto.getId() + "/schedule");
            trainService.addTrain(trainDto);
        }
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
        trainDto = trainService.updateTrainDto(trainDto);
        if (!scheduleService.getSchedulesByTrain(trainDto).isEmpty()) {
            scheduleDtoList = scheduleService.getSchedulesByTrain(trainDto);
        } else {
            for (StationDto stationDto : stationDtoList) {
                ScheduleDto scheduleDto = new ScheduleDto();
                scheduleDto.setStation(stationDto);
                scheduleDto.setTrain(trainDto);
                scheduleDtoList.add(scheduleDto);
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
        for (int i = 0; i < mappingService.getOrderedStationsByTrack(trainDto.getTrack()).size(); i++) {
            ScheduleDto scheduleDto = new ScheduleDto();
            scheduleDto.setId(Integer.parseInt(requestParams.get("id" + i)));
            scheduleDto.setStation(stationService.getStationByName(requestParams.get("station" + i)));
            scheduleDto.setTrain(trainService.updateTrainDto(trainDto));
            scheduleDto.setTrainStatus("On Schedule");
            scheduleDto.setArrivalTime(requestParams.get("arrivalTime" + i));
            scheduleDto.setDepartureTime(requestParams.get("departureTime" + i));
            scheduleDto.setDirection(!Boolean.parseBoolean(requestParams.get("isReversed")));
            scheduleService.editSchedule(scheduleDto);
        }
        modelAndView.setViewName("redirect:/trains");
        messagingService.sendMessage();
        return modelAndView;
    }

    @GetMapping(value = "/trains/delete/{id}")
    public ModelAndView deleteTrain(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/trains");
        trainService.deleteTrain(trainService.getTrain(id));
        return modelAndView;
    }
}