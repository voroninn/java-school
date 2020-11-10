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
public class SectionDto implements Serializable {

    private int id;

    private StationDto stationFrom;

    private StationDto stationTo;

    private double length;

    private TrackDto track;

    private boolean direction;
}