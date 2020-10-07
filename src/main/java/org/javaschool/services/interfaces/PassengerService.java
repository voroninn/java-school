package org.javaschool.services.interfaces;

import org.javaschool.entities.PassengerEntity;

import java.util.List;

public interface PassengerService {

    PassengerEntity getPassenger(int id);

    List<PassengerEntity> getAllPassengers();

    void addPassenger(PassengerEntity passenger);

    void editPassenger(PassengerEntity passenger);

    void deletePassenger(PassengerEntity passenger);
}
