package org.javaschool.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.javaschool.dao.interfaces.TrainDao;
import org.javaschool.dto.ScheduleDto;
import org.javaschool.dto.TrackDto;
import org.javaschool.dto.TrainDto;
import org.javaschool.mapper.TrackMapper;
import org.javaschool.mapper.TrainMapper;
import org.javaschool.services.interfaces.MessagingService;
import org.javaschool.services.interfaces.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TrainServiceImpl implements TrainService {

    private final TrainDao trainDao;
    private final TrainMapper trainMapper;
    private final TrackMapper trackMapper;
    private final MessagingService messagingService;

    @Override
    public TrainDto getTrain(int id) {
        return trainMapper.toDto(trainDao.getTrain(id));
    }

    @Override
    public TrainDto getTrainByName(String name) {
        return trainMapper.toDto(trainDao.getTrainByName(name));
    }

    @Override
    public List<TrainDto> getAllTrains() {
        return trainMapper.toDtoList(trainDao.getAllTrains());
    }

    @Override
    @Transactional
    public void addTrain(TrainDto trainDto) {
        trainDao.addTrain(trainMapper.toEntity(trainDto));
        log.info("Created new train " + trainDto.getName());
    }

    @Override
    @Transactional
    public void editTrain(TrainDto trainDto) {
        trainDao.editTrain(trainMapper.toEntity(trainDto));
        log.info("Updated train " + trainDto.getName());
    }

    @Override
    @Transactional
    public void deleteTrain(TrainDto trainDto) {
        trainDao.deleteTrain(trainMapper.toEntity(trainDto));
        log.info("Deleted train " + trainDto.getName());
        messagingService.sendMessage();
    }

    @Override
    public Set<TrainDto> getTrainsBySchedule(List<ScheduleDto> scheduleDtoList) {
        Set<TrainDto> trainDtoSet = new HashSet<>();
        TrainDto comparedTrain = scheduleDtoList.get(0).getTrain();
        trainDtoSet.add(comparedTrain);
        for (int i = 1; i < scheduleDtoList.size(); i++) {
            if (!scheduleDtoList.get(i).getTrain().equals(comparedTrain)) {
                trainDtoSet.add(scheduleDtoList.get(i).getTrain());
                comparedTrain = scheduleDtoList.get(i).getTrain();
            }
        }
        return trainDtoSet;
    }

    @Override
    public List<TrainDto> getTrainsByTrack(TrackDto trackDto) {
        return trainMapper.toDtoList(trainDao.getTrainsByTrack(trackMapper.toEntity(trackDto)));
    }

    @Override
    public TrainDto updateTrainDto(TrainDto trainDto) {
        return getTrainByName(trainDto.getName());
    }
}