package org.javaschool.dao.interfaces;

import org.javaschool.entities.ScheduleEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TrainEntity;

import java.util.List;

public interface ScheduleDao {

    ScheduleEntity getSchedule(int id);

    List<ScheduleEntity> getAllSchedules();

    void addSchedule(ScheduleEntity schedule);

    void editSchedule(ScheduleEntity schedule);

    void deleteSchedule(ScheduleEntity schedule);

    List<ScheduleEntity> getSchedulesByStationAndDirection(StationEntity station, boolean direction);

    List<ScheduleEntity> getSchedulesByRoute(List<StationEntity> route);
}
