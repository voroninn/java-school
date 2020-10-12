package org.javaschool.services.impl;

import org.javaschool.dao.interfaces.MappingDao;
import org.javaschool.entities.MappingEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TrackEntity;
import org.javaschool.services.interfaces.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MappingServiceImpl implements MappingService {

    @Autowired
    private MappingDao mappingDao;

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
        mappingDao.deleteMapping(mapping);
    }

    @Override
    public List<StationEntity> getOrderedStationsByTrack(TrackEntity track) {
        return mappingDao.getOrderedStationsByTrack(track);
    }
}
