package org.javaschool.services.interfaces;

import org.javaschool.dto.StationDto;
import org.javaschool.dto.TrainDto;

import java.util.LinkedList;
import java.util.List;

public interface StationService {

    StationDto getStation(int id);

    StationDto getStationByName(String username);

    List<StationDto> getAllStations();

    void addStation(StationDto stationDto);

    void editStation(StationDto stationDto);

    void deleteStation(StationDto stationDto);

    LinkedList<StationDto> getRoute(String stationFrom, String stationTo);

    int countTrackChanges(LinkedList<StationDto> route);

    List<StationDto> getStationsByTrain(TrainDto trainDto);

    List<StationDto> selectEndpoints(List<StationDto> stationDtoList);

    void setEndpoints(LinkedList<StationDto> route);

    StationDto updateStationDto(StationDto stationDto);
}