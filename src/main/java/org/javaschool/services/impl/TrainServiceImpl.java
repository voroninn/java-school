package org.javaschool.services.impl;

import org.javaschool.dao.interfaces.TrainDao;
import org.javaschool.entities.ScheduleEntity;
import org.javaschool.entities.TrainEntity;
import org.javaschool.services.interfaces.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public Set<TrainEntity> getTrainsBySchedules(List<ScheduleEntity> schedules) {
        Set<TrainEntity> trains = new HashSet<>();
        trains.add(schedules.get(0).getTrain());
        TrainEntity comparedTrain = schedules.get(0).getTrain();
        for (int i = 1; i < schedules.size(); i++) {
            if (!schedules.get(i).getTrain().equals(comparedTrain)) {
                trains.add(schedules.get(i).getTrain());
                comparedTrain = schedules.get(i).getTrain();
            }
        }
        return trains;
    }
}
