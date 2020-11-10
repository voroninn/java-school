package org.javaschool.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.javaschool.dao.interfaces.PassengerDao;
import org.javaschool.dto.PassengerDto;
import org.javaschool.dto.UserDto;
import org.javaschool.mapper.PassengerMapper;
import org.javaschool.mapper.TrainMapper;
import org.javaschool.mapper.UserMapper;
import org.javaschool.services.interfaces.PassengerService;
import org.javaschool.services.interfaces.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PassengerServiceImpl implements PassengerService {

    private final PassengerDao passengerDao;
    private final TrainService trainService;
    private final PassengerMapper passengerMapper;
    private final TrainMapper trainMapper;
    private final UserMapper userMapper;

    @Override
    public PassengerDto getPassenger(int id) {
        return passengerMapper.toDto(passengerDao.getPassenger(id));
    }

    @Override
    public List<PassengerDto> getAllPassengers() {
        return passengerMapper.toDtoList(passengerDao.getAllPassengers());
    }

    @Override
    public List<PassengerDto> getPassengersByTrainId(int trainId) {
        return passengerMapper.toDtoList(passengerDao.getPassengersByTrain(trainMapper.toEntity(trainService.getTrain(trainId))));
    }

    @Override
    public PassengerDto getPassengerByUser(UserDto userDto) {
        return passengerMapper.toDto(passengerDao.getPassengerByUser(userMapper.toEntity(userDto)));
    }

    @Override
    @Transactional
    public void addPassenger(PassengerDto passengerDto) {
        passengerDao.addPassenger(passengerMapper.toEntity(passengerDto));
        log.info("Created new passenger " + passengerDto.getFirstName() + " " + passengerDto.getLastName());
    }

    @Override
    @Transactional
    public void editPassenger(PassengerDto passengerDto) {
        passengerDao.editPassenger(passengerMapper.toEntity(passengerDto));
        log.info("Edited passenger " + passengerDto.getFirstName() + " " + passengerDto.getLastName());
    }

    @Override
    @Transactional
    public void deletePassenger(PassengerDto passengerDto) {
        passengerDao.deletePassenger(passengerMapper.toEntity(passengerDto));
        log.info("Deleted passenger " + passengerDto.getFirstName() + " " + passengerDto.getLastName());
    }
}