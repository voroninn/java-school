package org.javaschool.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOGGER = LogManager.getLogger(PassengerServiceImpl.class);

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
    public List<PassengerEntity> getPassengersByTrainId(int trainId) {
        return passengerDao.getPassengersByTrain(trainService.getTrain(trainId));
    }

    @Override
    @Transactional
    public PassengerEntity getPassengerByUser(UserEntity user) {
        return passengerDao.getPassengerByUser(user);
    }

    @Override
    @Transactional
    public void addPassenger(PassengerEntity passenger) {
        passengerDao.addPassenger(passenger);
        LOGGER.info("Created new passenger " + passenger.getFirstName() + " " + passenger.getLastName());
    }

    @Override
    @Transactional
    public void editPassenger(PassengerEntity passenger) {
        passengerDao.editPassenger(passenger);
        LOGGER.info("Edited passenger " + passenger.getFirstName() + " " + passenger.getLastName());
    }

    @Override
    @Transactional
    public void deletePassenger(PassengerEntity passenger) {
        passengerDao.deletePassenger(passenger);
        LOGGER.info("Deleted passenger " + passenger.getFirstName() + " " + passenger.getLastName());
    }
}
