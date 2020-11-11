package org.javaschool.services.interfaces;

import org.javaschool.dto.PassengerDto;
import org.javaschool.dto.UserDto;

import java.util.List;

public interface PassengerService {

    PassengerDto getPassenger(int id);

    List<PassengerDto> getAllPassengers();

    PassengerDto getPassengerByUser(UserDto userDto);

    void addPassenger(PassengerDto passengerDto);

    void editPassenger(PassengerDto passengerDto);

    void deletePassenger(PassengerDto passengerDto);
}