package org.javaschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimetableScheduleDto implements Serializable {

    private int id;

    private String stationName;

    private String trainName;

    private String arrivalTime;

    private String departureTime;

    private boolean direction;

    private String endStation;
}