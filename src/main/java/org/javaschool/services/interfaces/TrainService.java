package org.javaschool.services.interfaces;

import org.javaschool.entities.TrainEntity;

import java.util.List;

public interface TrainService {

    TrainEntity getTrain(int id);

    List<TrainEntity> getAllTrains();

    void addTrain(TrainEntity train);

    void editTrain(TrainEntity train);

    void deleteTrain(TrainEntity train);
}
