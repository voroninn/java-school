package org.javaschool.services.impl;

import org.javaschool.dao.interfaces.TrainDao;
import org.javaschool.entities.ScheduleEntity;
import org.javaschool.entities.TrackEntity;
import org.javaschool.entities.TrainEntity;
import org.javaschool.services.interfaces.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TrainDao trainDao;

    @Override
    public TrainEntity getTrain(int id) {
        return trainDao.getTrain(id);
    }

    @Override
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
    public Set<TrainEntity> getTrainsBySchedule(List<ScheduleEntity> schedule) {
        Set<TrainEntity> trains = new HashSet<>();
        TrainEntity comparedTrain = schedule.get(0).getTrain();
        trains.add(comparedTrain);
        for (int i = 1; i < schedule.size(); i++) {
            if (!schedule.get(i).getTrain().equals(comparedTrain)) {
                trains.add(schedule.get(i).getTrain());
                comparedTrain = schedule.get(i).getTrain();
            }
        }
        return trains;
    }

    @Override
    public List<TrainEntity> getTrainsByTrack(TrackEntity track) {
        return trainDao.getTrainsByTrack(track);
    }
}
