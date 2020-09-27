package org.javaschool.dao;

import org.javaschool.entities.PassengerEntity;
import org.javaschool.entities.TrainEntity;

import java.util.List;

public interface TrainDao {
    TrainEntity getTrain(int id);

    List<TrainEntity> getAllTrains();

    void addTrain(TrainEntity train);

    void updateTrain(TrainEntity train);

    void deleteTrain(TrainEntity train);
}