package org.javaschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {

    private int id;

    private String firstName;

    private String lastName;

    private String birthDate;

    private int passportNumber;

    private UserDto user;

    private Set<TicketDto> tickets;
}