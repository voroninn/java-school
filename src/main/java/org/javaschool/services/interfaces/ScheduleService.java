package org.javaschool.services.interfaces;

import org.javaschool.entities.ScheduleEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TrainEntity;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface ScheduleService {

    ScheduleEntity getSchedule(int id);

    List<ScheduleEntity> getAllSchedules();

    void addSchedule(ScheduleEntity schedule);

    void editSchedule(ScheduleEntity schedule);

    void deleteSchedule(ScheduleEntity schedule);

    List<ScheduleEntity> getSchedulesByStationAndDirection(StationEntity station, boolean direction);

    List<ScheduleEntity> getSchedulesByRoute(List<StationEntity> route);

    List<ScheduleEntity> orderSchedulesByTime(List<ScheduleEntity> schedules);

    List<ScheduleEntity> getSchedulesByTrain(TrainEntity train);

    List<ScheduleEntity> getSchedulesByStation(StationEntity station);

    List<List<ScheduleEntity>> getAllSchedulesByStations(List<StationEntity> stations);

    Date convertStringtoDate(String date);

    List<ScheduleEntity> buildSchedule(List<StationEntity> route, Date minDepartureTime);

    public void setBreakpoints(List<StationEntity> route);

    public void createEmptyScheduleForStation(StationEntity station, int trackId);
}
