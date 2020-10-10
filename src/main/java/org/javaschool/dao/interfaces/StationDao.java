package org.javaschool.dao.interfaces;

import org.javaschool.entities.ScheduleEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TrainEntity;

import java.util.List;

public interface StationDao {

    StationEntity getStation(int id);

    StationEntity getStationByName(String username);

    List<StationEntity> getAllStations();

    void addStation(StationEntity station);

    void editStation(StationEntity station);

    void deleteStation(StationEntity station);

    List<StationEntity> getStationsByTrain(TrainEntity train);
}
