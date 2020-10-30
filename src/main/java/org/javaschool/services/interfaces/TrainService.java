package org.javaschool.services.interfaces;

import org.javaschool.dto.ScheduleDto;
import org.javaschool.dto.TrackDto;
import org.javaschool.dto.TrainDto;

import java.util.List;
import java.util.Set;

public interface TrainService {

    TrainDto getTrain(int id);

    List<TrainDto> getAllTrains();

    void addTrain(TrainDto trainDto);

    void editTrain(TrainDto trainDto);

    void deleteTrain(TrainDto trainDto);

    Set<TrainDto> getTrainsBySchedule(List<ScheduleDto> scheduleDtoList);

    List<TrainDto> getTrainsByTrack(TrackDto trackDto);
}