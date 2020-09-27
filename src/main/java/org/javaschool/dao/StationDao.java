package org.javaschool.dao;

import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TrainEntity;

import java.util.List;

public interface StationDao {

    StationEntity getStation(int id);

    List<StationEntity> getAllStations();

    void addStation(StationEntity station);

    void updateStation(StationEntity station);

    void deleteStation(StationEntity station);
}