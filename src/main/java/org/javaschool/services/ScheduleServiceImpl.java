package org.javaschool.services;

import org.javaschool.dao.ScheduleDao;
import org.javaschool.entities.ScheduleEntity;
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
}
