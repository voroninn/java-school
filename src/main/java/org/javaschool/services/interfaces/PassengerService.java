package org.javaschool.services.interfaces;

import org.javaschool.entities.PassengerEntity;
import org.javaschool.entities.UserEntity;

import java.util.List;

public interface PassengerService {

    PassengerEntity getPassenger(int id);

    List<PassengerEntity> getAllPassengers();

    List<PassengerEntity> getPassengersByTrainId(int trainId);

    PassengerEntity getPassengerByUser(UserEntity user);

    void addPassenger(PassengerEntity passenger);

    void editPassenger(PassengerEntity passenger);

    void deletePassenger(PassengerEntity passenger);
}