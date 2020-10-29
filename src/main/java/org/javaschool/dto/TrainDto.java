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
public class TrainDto {

    private int id;

    private String name;

    private int capacity;

    private Set<ScheduleDto> schedules;

    private Set<TicketDto> tickets;

    private TrackDto track;
}