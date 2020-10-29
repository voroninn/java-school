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
public class StationDto {

    private int id;

    private String name;

    private boolean isEndpoint;

    private boolean isBreakpoint;

    private Set<ScheduleDto> schedules;

    private Set<MappingDto> mappings;
}