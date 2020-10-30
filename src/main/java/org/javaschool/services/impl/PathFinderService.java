package org.javaschool.services.impl;

import lombok.extern.log4j.Log4j2;
import org.javaschool.dto.SectionDto;
import org.javaschool.dto.StationDto;
import org.javaschool.services.interfaces.SectionService;
import org.javaschool.services.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
public class PathFinderService {

    @Autowired
    StationService stationService;

    @Autowired
    SectionService sectionService;

    private List<SectionDto> sections;
    private Set<StationDto> settledStations;
    private Set<StationDto> unsettledStations;
    private Map<StationDto, StationDto> predecessors;
    private Map<StationDto, Double> distance;

    public void initialize(StationDto source) {
        sections = sectionService.getAllSections();
        settledStations = new HashSet<>();
        unsettledStations = new HashSet<>();
        predecessors = new HashMap<>();
        distance = new HashMap<>();
        distance.put(source, 0.0);
        unsettledStations.add(source);
        while (unsettledStations.size() > 0) {
            StationDto station = getMinimum(unsettledStations);
            settledStations.add(station);
            unsettledStations.remove(station);
            findMinimumDistances(station);
        }
    }

    private void findMinimumDistances(StationDto station) {
        List<StationDto> adjacentStations = getNeighbors(station);
        for (StationDto target : adjacentStations) {
            if (getShortestDistance(target) > getShortestDistance(station)
                    + getDistance(station, target)) {
                distance.put(target, getShortestDistance(station)
                        + getDistance(station, target));
                predecessors.put(target, station);
                unsettledStations.add(target);
            }
        }
    }

    private double getDistance(StationDto station, StationDto target) {
        for (SectionDto section : sections) {
            if (section.getStationFrom().equals(station)
                    && section.getStationTo().equals(target)) {
                return section.getLength();
            }
        }
        throw new RuntimeException("Something went wrong");
    }

    private List<StationDto> getNeighbors(StationDto station) {
        List<StationDto> neighbors = new ArrayList<>();
        for (SectionDto section : sections) {
            if (section.getStationFrom().equals(station)
                    && !isSettled(section.getStationTo())) {
                neighbors.add(section.getStationTo());
            }
        }
        return neighbors;
    }

    private StationDto getMinimum(Set<StationDto> stations) {
        StationDto minimum = null;
        for (StationDto station : stations) {
            if (minimum == null) {
                minimum = station;
            } else {
                if (getShortestDistance(station) < getShortestDistance(minimum)) {
                    minimum = station;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(StationDto station) {
        return settledStations.contains(station);
    }

    private double getShortestDistance(StationDto destination) {
        Double d = distance.get(destination);
        if (d == null) {
            return Double.MAX_VALUE;
        } else {
            return d;
        }
    }

    public LinkedList<StationDto> createRoute(StationDto target) {
        LinkedList<StationDto> path = new LinkedList<>();
        StationDto step = target;
        if (predecessors.get(step) == null) {
            log.error("Route not found");
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        Collections.reverse(path);
        return path;
    }
}