package org.javaschool.services.impl;

import lombok.extern.log4j.Log4j2;
import org.javaschool.dao.interfaces.StationDao;
import org.javaschool.entities.SectionEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TrainEntity;
import org.javaschool.services.interfaces.SectionService;
import org.javaschool.services.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Log4j2
public class StationServiceImpl implements StationService {

    @Autowired
    private StationDao stationDao;

    @Autowired
    private PathFinderService pathFinder;

    @Autowired
    private SectionService sectionService;

    @Override
    @Transactional
    public StationEntity getStation(int id) {
        return stationDao.getStation(id);
    }

    @Override
    @Transactional
    public StationEntity getStationByName(String username) {
        return stationDao.getStationByName(username);
    }

    @Override
    @Transactional
    public List<StationEntity> getAllStations() {
        return stationDao.getAllStations();
    }

    @Override
    @Transactional
    public void addStation(StationEntity station) {
        stationDao.addStation(station);
        log.info("Created new station " + station.getName());
    }

    @Override
    @Transactional
    public void editStation(StationEntity station) {
        stationDao.editStation(station);
        log.info("Edited station " + station.getName());
    }

    @Override
    @Transactional
    public void deleteStation(StationEntity station) {
        stationDao.deleteStation(station);
        log.info("Deleted station " + station.getName());
    }

    public LinkedList<StationEntity> getRoute(String stationFrom, String stationTo) {
        pathFinder.initialize(getStationByName(stationFrom));
        return pathFinder.createRoute(getStationByName(stationTo));
    }

    public int countTrackChanges(LinkedList<StationEntity> route) {
        int counter = 0;
        for (int i = 0; i < route.size() - 2; i++) {
            SectionEntity section1 = sectionService.getSectionBetweenStations(route.get(i), route.get(i + 1));
            SectionEntity section2 = sectionService.getSectionBetweenStations(route.get(i + 1), route.get(i + 2));
            if (!section1.getTrack().equals(section2.getTrack())) {
                route.get(i + 1).setBreakpoint(true);
                route.get(i + 1).setEndpoint(false);
                counter++;
            }
        }
        return counter;
    }

    @Override
    @Transactional
    public List<StationEntity> getStationsByTrain(TrainEntity train) {
        return stationDao.getStationsByTrain(train);
    }

    @Override
    public List<StationEntity> selectEndpoints(List<StationEntity> stations) {
        List<StationEntity> endpoints = new ArrayList<>();
        endpoints.add(stations.get(0));
        endpoints.add(stations.get(stations.size() - 1));
        return endpoints;
    }

    @Override
    public void setEndpoints(LinkedList<StationEntity> route) {
        route.get(0).setEndpoint(true);
        route.get(route.size() - 1).setEndpoint(true);
    }
}