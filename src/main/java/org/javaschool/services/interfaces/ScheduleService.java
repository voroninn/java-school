package org.javaschool.services.interfaces;

import org.javaschool.entities.ScheduleEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TrainEntity;

import java.util.Date;
import java.util.List;

public interface ScheduleService {

    ScheduleEntity getSchedule(int id);

    List<ScheduleEntity> getAllSchedules();

    void addSchedule(ScheduleEntity schedule);

    void editSchedule(ScheduleEntity schedule);

    void deleteSchedule(ScheduleEntity schedule);

    List<ScheduleEntity> getSchedulesByStationAndDirection(StationEntity station, boolean direction);

    List<ScheduleEntity> getSchedulesByRoute(List<StationEntity> route);

    List<ScheduleEntity> putSchedulesInCorrectOrder(List<ScheduleEntity> schedules);

    List<List<ScheduleEntity>> separateSchedules(List<ScheduleEntity> schedules, int itemsPerSchedule);

    List<ScheduleEntity> getSchedulesByTrain(TrainEntity train);

    Date convertStringtoDate(String date);
}
