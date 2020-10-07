package org.javaschool.services;

import org.javaschool.entities.ScheduleEntity;
import org.javaschool.entities.StationEntity;

import java.util.List;

public interface ScheduleService {

    ScheduleEntity getSchedule(int id);

    List<ScheduleEntity> getAllSchedules();

    void addSchedule(ScheduleEntity schedule);

    void editSchedule(ScheduleEntity schedule);

    void deleteSchedule(ScheduleEntity schedule);

    List<ScheduleEntity> getSchedulesByStationAndDirection(StationEntity station, boolean direction);

    List<ScheduleEntity> getSchedulesByRoute(List<StationEntity> route);
}
