package org.javaschool.dao;

import org.javaschool.entities.TrainEntity;

import java.util.List;

public interface TrainDao {

    TrainEntity getTrain(int id);

    List<TrainEntity> getAllTrains();

    void addTrain(TrainEntity train);

    void editTrain(TrainEntity train);

    void deleteTrain(TrainEntity train);
}
