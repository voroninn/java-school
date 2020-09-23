package org.javaschool.services;

import org.javaschool.dao.StationDao;
import org.javaschool.entities.StationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StationServiceImpl  implements StationService {

    @Autowired
    private StationDao stationDao;

    @Override
    @Transactional
    public StationEntity getStation(int id) {
        return stationDao.getStation(id);
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
    public void updateStation(StationEntity station) {
        stationDao.updateStation(station);
    }

    @Override
    @Transactional
    public void deleteStation(StationEntity station) {
        stationDao.deleteStation(station);
    }
}
