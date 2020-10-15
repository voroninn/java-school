package org.javaschool.dao.interfaces;

import org.javaschool.entities.PassengerEntity;
import org.javaschool.entities.TrainEntity;
import org.javaschool.entities.UserEntity;

import java.util.List;

public interface PassengerDao {

    PassengerEntity getPassenger(int id);

    List<PassengerEntity> getAllPassengers();

    List<PassengerEntity> getPassengersByTrain(TrainEntity train);

    void addPassenger(PassengerEntity passenger);

    void editPassenger(PassengerEntity passenger);

    void deletePassenger(PassengerEntity passenger);

    PassengerEntity getPassengerByUser(UserEntity user);
}
