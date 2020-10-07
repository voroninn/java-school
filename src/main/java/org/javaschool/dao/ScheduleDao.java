package org.javaschool.dao;

import org.javaschool.entities.ScheduleEntity;
import org.javaschool.entities.SectionEntity;

import java.util.List;

public interface ScheduleDao {

    ScheduleEntity getSchedule(int id);

    List<ScheduleEntity> getAllSchedules();

    void addSchedule(ScheduleEntity schedule);

    void editSchedule(ScheduleEntity schedule);

    void deleteSchedule(ScheduleEntity schedule);
}
