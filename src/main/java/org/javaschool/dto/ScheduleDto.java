package org.javaschool.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto implements Serializable {

    private int id;

    private StationDto station;

    private TrainDto train;

    private String arrivalTime;

    private String departureTime;

    private boolean direction;

    private String endStation;

    public ScheduleDto(StationDto stationDto, TrainDto trainDto) {
    }
}