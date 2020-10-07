package org.javaschool.services;

import org.javaschool.entities.StationEntity;

import java.util.LinkedList;
import java.util.List;

public interface StationService {

    StationEntity getStation(int id);

    StationEntity getStationByName(String username);

    List<StationEntity> getAllStations();

    void addStation(StationEntity station);

    void editStation(StationEntity station);

    void deleteStation(StationEntity station);

    LinkedList<StationEntity> getRoute(String stationFrom, String stationTo);

    int countTrackChanges(LinkedList<StationEntity> route);
}
