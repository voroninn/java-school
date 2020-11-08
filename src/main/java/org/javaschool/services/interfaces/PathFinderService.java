package org.javaschool.services.interfaces;

import org.javaschool.dto.StationDto;

import java.util.LinkedList;

public interface PathFinderService {

    void initialize(StationDto source);

    LinkedList<StationDto> createRoute(StationDto target);
}
