package org.javaschool.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javaschool.dao.interfaces.ScheduleDao;
import org.javaschool.entities.*;
import org.javaschool.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private TrackService trackService;

    @Autowired
    private TrainService trainService;

    private static final Logger LOGGER = LogManager.getLogger(ScheduleServiceImpl.class);

    @Override
    @Transactional
    public ScheduleEntity getSchedule(int id) {
        return scheduleDao.getSchedule(id);
    }

    @Override
    @Transactional
    public List<ScheduleEntity> getAllSchedules() {
        return scheduleDao.getAllSchedules();
    }

    @Override
    @Transactional
    public void addSchedule(ScheduleEntity schedule) {
        scheduleDao.addSchedule(schedule);
        LOGGER.info("Created new schedule for " + schedule.getTrain().getName() + " at " + schedule.getStation());
    }

    @Override
    @Transactional
    public void editSchedule(ScheduleEntity schedule) {
        scheduleDao.editSchedule(schedule);
        LOGGER.info("Edited schedule for " + schedule.getTrain().getName() + " at " + schedule.getStation());
    }

    @Override
    @Transactional
    public void deleteSchedule(ScheduleEntity schedule) {
        scheduleDao.deleteSchedule(schedule);
        LOGGER.info("Deleted schedule for " + schedule.getTrain().getName() + " at " + schedule.getStation());
    }

    @Override
    @Transactional
    public List<ScheduleEntity> getSchedulesByStationAndDirection(StationEntity station, boolean direction) {
        return scheduleDao.getSchedulesByStationAndDirection(station, direction);
    }

    @Override
    @Transactional
    public List<ScheduleEntity> getSchedulesByRoute(List<StationEntity> route) {
        List<ScheduleEntity> schedulesByRoute = new ArrayList<>();
        List<SectionEntity> sectionsByRoute = sectionService.getSectionsByRoute(route);
        for (SectionEntity section : sectionsByRoute) {
            schedulesByRoute.addAll(getSchedulesByStationAndDirection(section.getStationFrom(), section.isDirection()));
        }
        SectionEntity lastSection = sectionsByRoute.get(sectionsByRoute.size() - 1);
        schedulesByRoute.addAll(getSchedulesByStationAndDirection(lastSection.getStationTo(), lastSection.isDirection()));
        return schedulesByRoute;
    }

    @Override
    public List<ScheduleEntity> orderSchedulesByTime(List<ScheduleEntity> schedules) {
        List<Date> arrivalTimes = new ArrayList<>();
        List<ScheduleEntity> orderedSchedules = new ArrayList<>();
        List<Date> afterMidnightTimes = new ArrayList<>();

        for (ScheduleEntity schedule : schedules) {
            if (schedule.getArrivalTime() == null) {
                orderedSchedules.add(schedule);
            } else if (schedule.getArrivalTime().equals(convertStringtoDate("00:00")) ||
                    (schedule.getArrivalTime().after(convertStringtoDate("00:00")) &&
                            schedule.getArrivalTime().before(convertStringtoDate("07:00")))) {
                afterMidnightTimes.add(schedule.getArrivalTime());
            } else {
                arrivalTimes.add(schedule.getArrivalTime());
            }
        }

        Collections.sort(arrivalTimes);
        Collections.sort(afterMidnightTimes);
        arrivalTimes.addAll(afterMidnightTimes);

        for (Date time : arrivalTimes) {
            for (ScheduleEntity schedule : schedules) {
                if (schedule.getArrivalTime() != null && schedule.getArrivalTime().equals(time)) {
                    orderedSchedules.add(schedule);
                    break;
                }
            }
        }
        return orderedSchedules;
    }

    @Override
    @Transactional
    public List<ScheduleEntity> getSchedulesByTrain(TrainEntity train) {
        return scheduleDao.getSchedulesByTrain(train);
    }

    @Override
    @Transactional
    public List<List<ScheduleEntity>> getAllSchedulesByStations(List<StationEntity> stations) {
        List<List<ScheduleEntity>> schedules = new ArrayList<>();
        for (StationEntity station : stations) {
            List<ScheduleEntity> subSchedules = new ArrayList<>();
            subSchedules.addAll(getSchedulesByStationAndDirection(station, true));
            subSchedules.addAll(getSchedulesByStationAndDirection(station, false));
            schedules.add(orderSchedulesByTime(subSchedules));
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
                LOGGER.error("Could not parse date from string");
            }
        }
        return parsedDate;
    }

    @Override
    public List<ScheduleEntity> buildSchedule(List<StationEntity> route, Date minDepartureTime) {
        List<ScheduleEntity> orderedSchedules = orderSchedulesByTime(getSchedulesByRoute(route));
        if (route.size() > 2) {
            setBreakpoints(route);
        }
        List<ScheduleEntity> schedules = new ArrayList<>();
        TrackEntity currentTrack;
        ScheduleEntity currentSchedule;
        mainLoop:
        for (int i = 0; i < route.size(); i++) {

            StationEntity currentStation = route.get(i);

            if (i != route.size() - 1) {
                StationEntity nextStation = route.get(i + 1);
                currentTrack = sectionService.getSectionBetweenStations(currentStation, nextStation).getTrack();
                for (ScheduleEntity schedule : orderedSchedules) {
                    if (currentStation.isBreakpoint()) {
                        if (schedule.getStation().getName().equals(currentStation.getName()) &&
                                schedule.getTrain().getTrack().equals(currentTrack) &&
                                schedule.getArrivalTime() != null &&
                                schedule.getArrivalTime().after(minDepartureTime)) {
                            currentSchedule = schedule;
                            minDepartureTime = schedule.getArrivalTime();
                            schedules.add(currentSchedule);
                            currentStation.setBreakpoint(false);
                            schedules.addAll(buildSchedule(route.subList(i, route.size()), minDepartureTime));
                            break mainLoop;
                        }
                    } else if (schedule.getStation().getName().equals(currentStation.getName()) &&
                            schedule.getTrain().getTrack().equals(currentTrack) &&
                            schedule.getDepartureTime() != null &&
                            schedule.getDepartureTime().after(minDepartureTime)) {
                        currentSchedule = schedule;
                        minDepartureTime = schedule.getDepartureTime();
                        schedules.add(currentSchedule);
                        break;
                    }
                }
            } else {
                StationEntity previousStation = route.get(i - 1);
                currentTrack = sectionService.getSectionBetweenStations(previousStation, currentStation).getTrack();
                for (ScheduleEntity schedule : orderedSchedules) {
                    if (schedule.getStation().getName().equals(currentStation.getName()) &&
                            schedule.getTrain().getTrack().equals(currentTrack) &&
                            schedule.getArrivalTime() != null &&
                            schedule.getArrivalTime().after(minDepartureTime)) {
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
    public void setBreakpoints(List<StationEntity> route) {
        for (int i = 0; i < route.size() - 2; i++) {
            SectionEntity section1 = sectionService.getSectionBetweenStations(route.get(i), route.get(i + 1));
            SectionEntity section2 = sectionService.getSectionBetweenStations(route.get(i + 1), route.get(i + 2));
            if (!section1.getTrack().equals(section2.getTrack())) {
                route.get(i + 1).setBreakpoint(true);
                route.get(i + 1).setEndpoint(false);
            }
        }
    }

    @Override
    @Transactional
    public void createEmptyScheduleForStation(StationEntity station, int trackId) {
        TrackEntity track = trackService.getTrack(trackId);
        List<TrainEntity> trains = trainService.getTrainsByTrack(track);
        for (TrainEntity train : trains) {
            if (getSchedulesByTrain(train).get(0).isDirection()) {
                scheduleDao.addSchedule(new ScheduleEntity(station, train, true));
            } else {
                scheduleDao.addSchedule(new ScheduleEntity(station, train, false));
            }
        }
    }
}