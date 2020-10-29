package org.javaschool.dto;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {

    private int id;

    private String number;

    private PassengerDto passenger;

    private Set<TrainDto> trains;

    private String departureStation;

    private String arrivalStation;

    private Date departureTime;

    private Date arrivalTime;

    private String date;

    private double price;
}