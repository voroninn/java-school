package org.javaschool.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MappingDto implements Serializable {

    private int id;

    private StationDto station;

    private TrackDto track;

    private int stationOrder;
}
