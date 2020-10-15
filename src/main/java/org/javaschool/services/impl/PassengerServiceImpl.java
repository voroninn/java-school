package org.javaschool.services.impl;

import org.javaschool.dao.interfaces.PassengerDao;
import org.javaschool.entities.PassengerEntity;
import org.javaschool.entities.UserEntity;
import org.javaschool.services.interfaces.PassengerService;
import org.javaschool.services.interfaces.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerDao passengerDao;

    @Autowired
    private TrainService trainService;

    @Override
    public PassengerEntity getPassenger(int id) {
        return passengerDao.getPassenger(id);
    }

    @Override
    public List<PassengerEntity> getAllPassengers() {
        return passengerDao.getAllPassengers();
    }

    @Override
    public List<PassengerEntity> getPassengersByTrainId(int trainId) {
        return passengerDao.getPassengersByTrain(trainService.getTrain(trainId));
    }

    @Override
    public PassengerEntity getPassengerByUser(UserEntity user) {
        return passengerDao.getPassengerByUser(user);
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
}
