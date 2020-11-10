package org.javaschool.services.impl;

import lombok.extern.log4j.Log4j2;
import org.javaschool.dao.interfaces.TrackDao;
import org.javaschool.dto.TrackDto;
import org.javaschool.mapper.TrackMapper;
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

    @Autowired
    private TrackMapper trackMapper;

    @Override
    public TrackDto getTrack(int id) {
        return trackMapper.toDto(trackDao.getTrack(id));
    }

    @Override
    public List<TrackDto> getAllTracks() {
        return trackMapper.toDtoList(trackDao.getAllTracks());
    }

    @Override
    @Transactional
    public void addTrack(TrackDto trackDto) {
        trackDao.addTrack(trackMapper.toEntity(trackDto));
        log.info("Created new track " + trackDto.getId());
    }

    @Override
    @Transactional
    public void editTrack(TrackDto trackDto) {
        trackDao.editTrack(trackMapper.toEntity(trackDto));
        log.info("Edited track " + trackDto.getId());
    }

    @Override
    @Transactional
    public void deleteTrack(TrackDto trackDto) {
        trackDao.deleteTrack(trackMapper.toEntity(trackDto));
        log.info("Deleted track " + trackDto.getId());
    }
}