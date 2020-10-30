package org.javaschool.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {

    private int id;

    private StationDto station;

    private TrainDto train;

    private String arrivalTime;

    private String departureTime;

    private boolean direction;

    public ScheduleDto(StationDto stationDto, TrainDto trainDto) {
    }
}