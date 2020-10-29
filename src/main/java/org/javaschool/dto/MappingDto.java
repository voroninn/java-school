package org.javaschool.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MappingDto {

    private int id;

    private StationDto station;

    private TrackDto track;

    private int stationOrder;
}
