package org.javaschool.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javaschool.entities.SectionEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.services.interfaces.SectionService;
import org.javaschool.services.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PathFinderService {

    @Autowired
    StationService stationService;

    @Autowired
    SectionService sectionService;

    private static final Logger LOGGER = LogManager.getLogger(PathFinderService.class);

    private List<SectionEntity> sections;
    private Set<StationEntity> settledStations;
    private Set<StationEntity> unsettledStations;
    private Map<StationEntity, StationEntity> predecessors;
    private Map<StationEntity, Double> distance;

    public void initialize(StationEntity source) {
        sections = sectionService.getAllSections();
        settledStations = new HashSet<>();
        unsettledStations = new HashSet<>();
        predecessors = new HashMap<>();
        distance = new HashMap<>();
        distance.put(source, 0.0);
        unsettledStations.add(source);
        while (unsettledStations.size() > 0) {
            StationEntity station = getMinimum(unsettledStations);
            settledStations.add(station);
            unsettledStations.remove(station);
            findMinimumDistances(station);
        }
    }

    private void findMinimumDistances(StationEntity station) {
        List<StationEntity> adjacentStations = getNeighbors(station);
        for (StationEntity target : adjacentStations) {
            if (getShortestDistance(target) > getShortestDistance(station)
                    + getDistance(station, target)) {
                distance.put(target, getShortestDistance(station)
                        + getDistance(station, target));
                predecessors.put(target, station);
                unsettledStations.add(target);
            }
        }
    }

    private double getDistance(StationEntity station, StationEntity target) {
        for (SectionEntity section : sections) {
            if (section.getStationFrom().equals(station)
                    && section.getStationTo().equals(target)) {
                return section.getLength();
            }
        }
        throw new RuntimeException("Something went wrong");
    }

    private List<StationEntity> getNeighbors(StationEntity station) {
        List<StationEntity> neighbors = new ArrayList<>();
        for (SectionEntity section : sections) {
            if (section.getStationFrom().equals(station)
                    && !isSettled(section.getStationTo())) {
                neighbors.add(section.getStationTo());
            }
        }
        return neighbors;
    }

    private StationEntity getMinimum(Set<StationEntity> stations) {
        StationEntity minimum = null;
        for (StationEntity station : stations) {
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

    private boolean isSettled(StationEntity station) {
        return settledStations.contains(station);
    }

    private double getShortestDistance(StationEntity destination) {
        Double d = distance.get(destination);
        if (d == null) {
            return Double.MAX_VALUE;
        } else {
            return d;
        }
    }

    public LinkedList<StationEntity> createRoute(StationEntity target) {
        LinkedList<StationEntity> path = new LinkedList<>();
        StationEntity step = target;
        if (predecessors.get(step) == null) {
            LOGGER.error("Route not found");
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
