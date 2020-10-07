package org.javaschool.dao.interfaces;

import org.javaschool.entities.PassengerEntity;

import java.util.List;

public interface PassengerDao {

    PassengerEntity getPassenger(int id);

    List<PassengerEntity> getAllPassengers();

    void addPassenger(PassengerEntity passenger);

    void editPassenger(PassengerEntity passenger);

    void deletePassenger(PassengerEntity passenger);
}