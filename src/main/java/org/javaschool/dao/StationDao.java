package org.javaschool.dao;

import org.javaschool.entities.StationEntity;

import java.util.List;

public interface StationDao {

    StationEntity getStation(int id);

    List<StationEntity> getAllStations();

    void addStation(StationEntity station);

    void editStation(StationEntity station);

    void deleteStation(StationEntity station);
}
