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
public class TrackDto {

    private int id;

    private Set<TrainDto> trains;

    private Set<MappingDto> mappings;
}