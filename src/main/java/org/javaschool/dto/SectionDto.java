package org.javaschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionDto {

    private int id;

    private StationDto stationFrom;

    private StationDto stationTo;

    private double length;

    private TrackDto track;

    private boolean direction;

    public SectionDto(StationDto stationFromDto, StationDto stationToDto, int length, TrackDto trackDto, boolean direction) {
    }
}