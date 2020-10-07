package org.javaschool.services.impl;

import org.javaschool.dao.interfaces.ScheduleDao;
import org.javaschool.entities.ScheduleEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.services.interfaces.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<ScheduleEntity> getSchedulesByRoute(List<StationEntity> route) {
        return scheduleDao.getSchedulesByRoute(route);
    }
}
