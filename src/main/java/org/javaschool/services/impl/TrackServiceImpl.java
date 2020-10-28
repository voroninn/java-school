package org.javaschool.services.impl;

import lombok.extern.log4j.Log4j2;
import org.javaschool.dao.interfaces.TrackDao;
import org.javaschool.entities.TrackEntity;
import org.javaschool.services.interfaces.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class TrackServiceImpl implements TrackService {

    @Autowired
    private TrackDao trackDao;

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
        log.info("Created new track " + track.getId());
    }

    @Override
    @Transactional
    public void editTrack(TrackEntity track) {
        trackDao.editTrack(track);
        log.info("Edited track " + track.getId());
    }

    @Override
    @Transactional
    public void deleteTrack(TrackEntity track) {
        trackDao.deleteTrack(track);
        log.info("Deleted track " + track.getId());
    }
}
