package org.javaschool.services.impl;

import org.javaschool.dao.interfaces.MappingDao;
import org.javaschool.entities.MappingEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TrackEntity;
import org.javaschool.services.interfaces.MappingService;
import org.javaschool.services.interfaces.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MappingServiceImpl implements MappingService {

    @Autowired
    private MappingDao mappingDao;

    @Autowired
    private TrackService trackService;

    @Override
    @Transactional
    public MappingEntity getMapping(int id) {
        return mappingDao.getMapping(id);
    }

    @Override
    @Transactional
    public List<MappingEntity> getAllMappings() {
        return mappingDao.getAllMappings();
    }

    @Override
    @Transactional
    public List<MappingEntity> getMappingsByTrack(TrackEntity track) {
        return mappingDao.getMappingsByTrack(track);
    }

    @Override
    @Transactional
    public void addMapping(MappingEntity mapping) {
        mappingDao.addMapping(mapping);
    }

    @Override
    @Transactional
    public void editMapping(MappingEntity mapping) {
        mappingDao.editMapping(mapping);
    }

    @Override
    @Transactional
    public void deleteMapping(MappingEntity mapping) {
        List<MappingEntity> mappings = getMappingsByTrack(mapping.getTrack());
        for (int i = mapping.getStationOrder(); i < mappings.size(); i++) {
            mappings.get(i).setStationOrder(i);
            mappingDao.editMapping(mappings.get(i));
        }
        mappingDao.deleteMapping(mapping);
    }

    @Override
    @Transactional
    public List<StationEntity> getOrderedStationsByTrack(TrackEntity track) {
        return mappingDao.getOrderedStationsByTrack(track);
    }

    @Override
    @Transactional
    public TrackEntity getTrack(int id) {
        return mappingDao.getTrack(id);
    }

    @Override
    @Transactional
    public void appendStation(StationEntity station, int trackId, String appendLocation) {
        TrackEntity track = trackService.getTrack(trackId);
        int stationOrder = 0;
        switch (appendLocation) {
            case "before":
                stationOrder = 1;
                List<MappingEntity> mappings = getMappingsByTrack(track);
                if (mappings.get(0).getStationOrder() == 1) {
                    for (MappingEntity mapping : mappings) {
                        mapping.setStationOrder(mapping.getStationOrder() + 1);
                        mappingDao.editMapping(mapping);
                    }
                }
                mappingDao.addMapping(new MappingEntity(station, track, stationOrder));
                break;
            case "after":
                stationOrder = getOrderedStationsByTrack(track).size() + 1;
                mappingDao.addMapping(new MappingEntity(station, track, stationOrder));
                break;
        }
    }

    @Override
    @Transactional
    public int getStationOrder(StationEntity station, TrackEntity track) {
        return mappingDao.getStationOrder(station, track);
    }

    @Override
    @Transactional
    public StationEntity getStationByOrder(TrackEntity track, int stationOrder) {
        return mappingDao.getStationByOrder(track, stationOrder);
    }
}