package org.javaschool.services.impl;

import org.javaschool.dao.interfaces.ScheduleDao;
import org.javaschool.entities.ScheduleEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TrainEntity;
import org.javaschool.services.interfaces.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleDao scheduleDao;

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
    }

    @Override
    @Transactional
    public void editSchedule(ScheduleEntity schedule) {
        scheduleDao.editSchedule(schedule);
    }

    @Override
    @Transactional
    public void deleteSchedule(ScheduleEntity schedule) {
        scheduleDao.deleteSchedule(schedule);
    }

    @Override
    @Transactional
    public List<ScheduleEntity> getSchedulesByStationAndDirection(StationEntity station, boolean direction) {
        return scheduleDao.getSchedulesByStationAndDirection(station, direction);
    }

    @Override
    @Transactional
    public List<ScheduleEntity> getSchedulesByRoute(List<StationEntity> route) {
        return scheduleDao.getSchedulesByRoute(route);
    }

    @Override
    public List<ScheduleEntity> putSchedulesInCorrectOrder(List<ScheduleEntity> schedules) {
        for (int i = 2; i < schedules.size(); i += 2) {
            for (int j = 1; j <= i / 2; j++)
                Collections.swap(schedules, i - j + 1, i - j);
        }
        return schedules;
    }

    @Override
    public List<List<ScheduleEntity>> separateSchedules(List<ScheduleEntity> schedules, int itemsPerSchedule) {
        List<List<ScheduleEntity>> separatedSchedules = new ArrayList<>();
        for (int i = 0; i <= itemsPerSchedule; i += itemsPerSchedule) {
            separatedSchedules.add(schedules.subList(i, i + itemsPerSchedule));
        }
        return separatedSchedules;
    }
}