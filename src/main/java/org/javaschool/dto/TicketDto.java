package org.javaschool.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto implements Serializable {

    private int id;

    private String number;

    private PassengerDto passenger;

    private Set<TrainDto> trains;

    private String departureStation;

    private String arrivalStation;

    private String departureTime;

    private String arrivalTime;

    private String date;

    private double price;
}