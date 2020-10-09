package org.javaschool.services.impl;

import org.javaschool.dao.interfaces.PassengerDao;
import org.javaschool.entities.PassengerEntity;
import org.javaschool.entities.UserEntity;
import org.javaschool.services.interfaces.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerDao passengerDao;

    @Override
    @Transactional
    public PassengerEntity getPassenger(int id) {
        return passengerDao.getPassenger(id);
    }

    @Override
    @Transactional
    public List<PassengerEntity> getAllPassengers() {
        return passengerDao.getAllPassengers();
    }

    @Override
    @Transactional
    public void addPassenger(PassengerEntity passenger) {
        passengerDao.addPassenger(passenger);
    }

    @Override
    @Transactional
    public void editPassenger(PassengerEntity passenger) {
        passengerDao.editPassenger(passenger);
    }

    @Override
    @Transactional
    public void deletePassenger(PassengerEntity passenger) {
        passengerDao.deletePassenger(passenger);
    }

    @Override
    @Transactional
    public PassengerEntity getPassengerByUser(UserEntity user) {
        return passengerDao.getPassengerByUser(user);
    }
}
