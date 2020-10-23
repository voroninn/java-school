package org.javaschool.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javaschool.dao.interfaces.TrackDao;
import org.javaschool.entities.TrackEntity;
import org.javaschool.services.interfaces.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrackServiceImpl implements TrackService {

    @Autowired
    private TrackDao trackDao;

    private static final Logger LOGGER = LogManager.getLogger(TrackServiceImpl.class);

    @Override
    @Transactional
    public TrackEntity getTrack(int id) {
        return trackDao.getTrack(id);
    }

    @Override
    @Transactional
    public List<TrackEntity> getAllTracks() {
        return trackDao.getAllTracks();
    }

    @Override
    @Transactional
    public void addTrack(TrackEntity track) {
        trackDao.addTrack(track);
        LOGGER.info("Created new track " + track.getId());
    }

    @Override
    @Transactional
    public void editTrack(TrackEntity track) {
        trackDao.editTrack(track);
        LOGGER.info("Edited track " + track.getId());
    }

    @Override
    @Transactional
    public void deleteTrack(TrackEntity track) {
        trackDao.deleteTrack(track);
        LOGGER.info("Deleted track " + track.getId());
    }
}
