package org.javaschool.dao;

import org.javaschool.entities.PassengerEntity;

import java.util.List;

public interface PassengerDao {
    PassengerEntity getPassenger(int id);

    List<PassengerEntity> getAllPassengers();

    void addPassenger(PassengerEntity passenger);

    void updatePassenger(PassengerEntity passenger);

    void deletePassenger(PassengerEntity passenger);
}
