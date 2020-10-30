package org.javaschool.services.impl;

import lombok.extern.log4j.Log4j2;
import org.javaschool.dao.interfaces.ScheduleDao;
import org.javaschool.dto.*;
import org.javaschool.entities.ScheduleEntity;
import org.javaschool.mapper.ScheduleMapper;
import org.javaschool.mapper.StationMapper;
import org.javaschool.mapper.TrainMapper;
import org.javaschool.services.interfaces.ScheduleService;
import org.javaschool.services.interfaces.SectionService;
import org.javaschool.services.interfaces.TrackService;
import org.javaschool.services.interfaces.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Log4j2
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private TrackService trackService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private MessagingService messagingService;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private TrainMapper trainMapper;

    @Override
    @Transactional
    public ScheduleDto getSchedule(int id) {
        return scheduleMapper.toDto(scheduleDao.getSchedule(id));
    }

    @Override
    @Transactional
    public List<ScheduleDto> getAllSchedules() {
        return scheduleMapper.toDtoList(scheduleDao.getAllSchedules());
    }

    @Override
    @Transactional
    public void addSchedule(ScheduleDto scheduleDto) {
        scheduleDao.addSchedule(scheduleMapper.toEntity(scheduleDto));
        log.info("Created new schedule for " +
                scheduleDto.getTrain().getName() + " on " + scheduleDto.getStation().getName());
    }

    @Override
    @Transactional
    public void editSchedule(ScheduleDto scheduleDto) {
        scheduleDao.editSchedule(scheduleMapper.toEntity(scheduleDto));
        log.info("Edited schedule for " +
                scheduleDto.getTrain().getName() + " on " + scheduleDto.getStation().getName());
        messagingService.sendMessage(scheduleMapper.toEntity(scheduleDto));
        log.info("Message sent to queue");
    }

    @Override
    @Transactional
    public void deleteSchedule(ScheduleDto scheduleDto) {
        scheduleDao.deleteSchedule(scheduleMapper.toEntity(scheduleDto));
        log.info("Deleted schedule for " +
                scheduleDto.getTrain().getName() + " on " + scheduleDto.getStation().getName());
    }

    @Override
    @Transactional
    public List<ScheduleDto> getSchedulesByStationAndDirection(StationDto stationDto, boolean direction) {
        return scheduleMapper.toDtoList(scheduleDao.getSchedulesByStationAndDirection(stationMapper.toEntity(stationDto), direction));
    }

    @Override
    @Transactional
    public List<ScheduleDto> getSchedulesByRoute(List<StationDto> route) {
        List<ScheduleDto> schedulesByRoute = new ArrayList<>();
        List<SectionDto> sectionsByRoute = sectionService.getSectionsByRoute(route);
        for (SectionDto section : sectionsByRoute) {
            schedulesByRoute.addAll(getSchedulesByStationAndDirection(section.getStationFrom(), section.isDirection()));
        }
        SectionDto lastSection = sectionsByRoute.get(sectionsByRoute.size() - 1);
        schedulesByRoute.addAll(getSchedulesByStationAndDirection(lastSection.getStationTo(), lastSection.isDirection()));
        return schedulesByRoute;
    }

    @Override
    public List<ScheduleDto> orderSchedulesByTime(List<ScheduleDto> scheduleDtoList) {
        List<Date> arrivalTimes = new ArrayList<>();
        List<ScheduleDto> orderedSchedules = new ArrayList<>();
        List<Date> afterMidnightTimes = new ArrayList<>();
        Date ScheduleArrivalTime;

        for (ScheduleDto schedule : scheduleDtoList) {
            ScheduleArrivalTime = convertStringtoDate(schedule.getArrivalTime());
            if (ScheduleArrivalTime == null) {
                orderedSchedules.add(schedule);
            } else if (ScheduleArrivalTime.equals(convertStringtoDate("00:00")) ||
                    (ScheduleArrivalTime.after(convertStringtoDate("00:00")) &&
                            ScheduleArrivalTime.before(convertStringtoDate("07:00")))) {
                afterMidnightTimes.add(ScheduleArrivalTime);
            } else {
                arrivalTimes.add(ScheduleArrivalTime);
            }
        }

        Collections.sort(arrivalTimes);
        Collections.sort(afterMidnightTimes);
        arrivalTimes.addAll(afterMidnightTimes);

        for (Date time : arrivalTimes) {
            for (ScheduleDto schedule : scheduleDtoList) {
                ScheduleArrivalTime = convertStringtoDate(schedule.getArrivalTime());
                if (ScheduleArrivalTime != null && ScheduleArrivalTime.equals(time)) {
                    orderedSchedules.add(schedule);
                    break;
                }
            }
        }
        return orderedSchedules;
    }

    @Override
    @Transactional
    public List<ScheduleDto> getSchedulesByTrain(TrainDto trainDto) {
        return scheduleMapper.toDtoList(scheduleDao.getSchedulesByTrain(trainMapper.toEntity(trainDto)));
    }

    @Override
    public List<ScheduleDto> getSchedulesByStation(StationDto stationDto) {
        List<ScheduleDto> schedules = new ArrayList<>();
        schedules.addAll(getSchedulesByStationAndDirection(stationDto, true));
        schedules.addAll(getSchedulesByStationAndDirection(stationDto, false));
        return orderSchedulesByTime(schedules);
    }

    @Override
    @Transactional
    public List<List<ScheduleDto>> getAllSchedulesByStations(List<StationDto> stationDtoList) {
        List<List<ScheduleDto>> schedules = new ArrayList<>();
        for (StationDto station : stationDtoList) {
            schedules.add(getSchedulesByStation(station));
        }
        return schedules;
    }

    @Override
    public Date convertStringtoDate(String date) {
        Date parsedDate = null;
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            try {
                parsedDate = formatter.parse(date);
            } catch (ParseException pE) {
                pE.getStackTrace();
                log.error("Could not parse date from string");
            }
        }
        return parsedDate;
    }

    @Override
    public List<ScheduleDto> buildSchedule(List<StationDto> route, Date minDepartureTime) {
        List<ScheduleDto> orderedSchedules = orderSchedulesByTime(getSchedulesByRoute(route));
        if (route.size() > 2) {
            setBreakpoints(route);
        }
        List<ScheduleDto> schedules = new ArrayList<>();
        TrackDto currentTrack;
        ScheduleDto currentSchedule;
        Date ScheduleArrivalTime;
        Date ScheduleDepartureTime;
        mainLoop:
        for (int i = 0; i < route.size(); i++) {

            StationDto currentStation = route.get(i);

            if (i != route.size() - 1) {
                StationDto nextStation = route.get(i + 1);
                currentTrack = sectionService.getSectionBetweenStations(currentStation, nextStation).getTrack();
                for (ScheduleDto schedule : orderedSchedules) {
                    ScheduleArrivalTime = convertStringtoDate(schedule.getArrivalTime());
                    ScheduleDepartureTime = convertStringtoDate(schedule.getDepartureTime());
                    if (currentStation.isBreakpoint()) {
                        if (schedule.getStation().getName().equals(currentStation.getName()) &&
                                schedule.getTrain().getTrack().equals(currentTrack) &&
                                ScheduleArrivalTime != null && ScheduleArrivalTime.after(minDepartureTime)) {
                            currentSchedule = schedule;
                            minDepartureTime = ScheduleArrivalTime;
                            schedules.add(currentSchedule);
                            currentStation.setBreakpoint(false);
                            schedules.addAll(buildSchedule(route.subList(i, route.size()), minDepartureTime));
                            break mainLoop;
                        }
                    } else if (schedule.getStation().getName().equals(currentStation.getName()) &&
                            schedule.getTrain().getTrack().equals(currentTrack) &&
                            ScheduleDepartureTime != null && ScheduleDepartureTime.after(minDepartureTime)) {
                        currentSchedule = schedule;
                        minDepartureTime = ScheduleDepartureTime;
                        schedules.add(currentSchedule);
                        break;
                    }
                }
            } else {
                StationDto previousStation = route.get(i - 1);
                currentTrack = sectionService.getSectionBetweenStations(previousStation, currentStation).getTrack();
                for (ScheduleDto schedule : orderedSchedules) {
                    ScheduleArrivalTime = convertStringtoDate(schedule.getArrivalTime());
                    if (schedule.getStation().getName().equals(currentStation.getName()) &&
                            schedule.getTrain().getTrack().equals(currentTrack) &&
                            ScheduleArrivalTime != null && ScheduleArrivalTime.after(minDepartureTime)) {
                        currentSchedule = schedule;
                        schedules.add(currentSchedule);
                        break;
                    }
                }
            }
        }
        return schedules;
    }

    @Override
    @Transactional
    public void setBreakpoints(List<StationDto> route) {
        for (int i = 0; i < route.size() - 2; i++) {
            SectionDto section1 = sectionService.getSectionBetweenStations(route.get(i), route.get(i + 1));
            SectionDto section2 = sectionService.getSectionBetweenStations(route.get(i + 1), route.get(i + 2));
            if (!section1.getTrack().equals(section2.getTrack())) {
                route.get(i + 1).setBreakpoint(true);
                route.get(i + 1).setEndpoint(false);
            }
        }
    }

    @Override
    @Transactional
    public void createEmptyScheduleForStation(StationDto stationDto, int trackId) {
        TrackDto track = trackService.getTrack(trackId);
        List<TrainDto> trainDtoList = trainService.getTrainsByTrack(track);
        for (TrainDto trainDto : trainDtoList) {
            if (getSchedulesByTrain(trainDto).get(0).isDirection()) {
                scheduleDao.addSchedule(new ScheduleEntity(stationMapper.toEntity(stationDto),
                        trainMapper.toEntity(trainDto), true));
            } else {
                scheduleDao.addSchedule(new ScheduleEntity(stationMapper.toEntity(stationDto),
                        trainMapper.toEntity(trainDto), false));
            }
        }
    }
}