package org.javaschool.services.interfaces;

import org.javaschool.dto.ScheduleDto;
import org.javaschool.dto.StationDto;
import org.javaschool.dto.TrainDto;

import java.util.Date;
import java.util.List;

public interface ScheduleService {

    ScheduleDto getSchedule(int id);

    List<ScheduleDto> getAllSchedules();

    void addSchedule(ScheduleDto scheduleDto);

    void editSchedule(ScheduleDto scheduleDto);

    void deleteSchedule(ScheduleDto scheduleDto);

    List<ScheduleDto> getSchedulesByStationAndDirection(StationDto stationDto, boolean direction);

    List<ScheduleDto> getSchedulesByRoute(List<StationDto> route);

    List<ScheduleDto> orderSchedulesByTime(List<ScheduleDto> scheduleDtoList);

    List<ScheduleDto> getSchedulesByTrain(TrainDto trainDto);

    List<ScheduleDto> getSchedulesByStation(StationDto stationDto);

    List<List<ScheduleDto>> getAllSchedulesByStations(List<StationDto> stationDtoList);

    Date convertStringtoDate(String date);

    List<ScheduleDto> buildSchedule(List<StationDto> route, Date minDepartureTime);

    public void setBreakpoints(List<StationDto> route);

    public void createEmptyScheduleForStation(StationDto stationDto, int trackId);
}