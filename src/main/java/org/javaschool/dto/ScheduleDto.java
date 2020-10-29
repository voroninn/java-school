package org.javaschool.dto;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {

    private int id;

    private StationDto station;

    private TrainDto train;

    private Date arrivalTime;

    private Date departureTime;

    private boolean direction;
}