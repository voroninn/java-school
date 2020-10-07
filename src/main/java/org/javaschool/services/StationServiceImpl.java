package org.javaschool.services;

import org.javaschool.dao.StationDao;
import org.javaschool.entities.SectionEntity;
import org.javaschool.entities.StationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
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
    }

    @Override
    @Transactional
    public void editStation(StationEntity station) {
        stationDao.editStation(station);
    }

    @Override
    @Transactional
    public void deleteStation(StationEntity station) {
        stationDao.deleteStation(station);
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
            if (section1.getTrack().equals(section2.getTrack())) {
                continue;
            }
            counter++;
        }
        return counter;
    }
}
