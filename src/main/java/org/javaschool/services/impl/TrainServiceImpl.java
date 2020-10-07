package org.javaschool.services.impl;

import org.javaschool.dao.interfaces.TrainDao;
import org.javaschool.entities.TrainEntity;
import org.javaschool.services.interfaces.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TrainDao trainDao;

    @Override
    @Transactional
    public TrainEntity getTrain(int id) {
        return trainDao.getTrain(id);
    }

    @Override
    @Transactional
    public List<TrainEntity> getAllTrains() {
        return trainDao.getAllTrains();
    }

    @Override
    @Transactional
    public void addTrain(TrainEntity train) {
        trainDao.addTrain(train);
    }

    @Override
    @Transactional
    public void editTrain(TrainEntity train) {
        trainDao.editTrain(train);
    }

    @Override
    @Transactional
    public void deleteTrain(TrainEntity train) {
        trainDao.deleteTrain(train);
    }
}