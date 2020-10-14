package org.javaschool.dao.interfaces;

import org.javaschool.entities.TrackEntity;
import org.javaschool.entities.TrainEntity;

import java.util.List;

public interface TrainDao {

    TrainEntity getTrain(int id);

    List<TrainEntity> getAllTrains();

    void addTrain(TrainEntity train);

    void editTrain(TrainEntity train);

    void deleteTrain(TrainEntity train);

    List<TrainEntity> getTrainsByTrack(TrackEntity track);
}
