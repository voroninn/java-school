package org.javaschool.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javaschool.dao.interfaces.SectionDao;
import org.javaschool.entities.SectionEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TrackEntity;
import org.javaschool.services.interfaces.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionDao sectionDao;

    private static final Logger LOGGER = LogManager.getLogger(SectionServiceImpl.class);

    @Override
    public SectionEntity getSection(int id) {
        return sectionDao.getSection(id);
    }

    @Override
    public List<SectionEntity> getAllSections() {
        return sectionDao.getAllSections();
    }

    @Override
    @Transactional
    public void addSection(SectionEntity section) {
        sectionDao.addSection(section);
        LOGGER.info("Created new section between " + section.getStationFrom() + " and " + section.getStationTo());
    }

    @Override
    @Transactional
    public void editSection(SectionEntity section) {
        sectionDao.editSection(section);
        LOGGER.info("Edited section between " + section.getStationFrom() + " and " + section.getStationTo());
    }

    @Override
    @Transactional
    public void deleteSection(SectionEntity section) {
        sectionDao.deleteSection(section);
        LOGGER.info("Deleted section between " + section.getStationFrom() + " and " + section.getStationTo());
    }

    @Override
    @Transactional
    public SectionEntity getSectionBetweenStations(StationEntity stationFrom, StationEntity stationTo) {
        return sectionDao.getSectionBetweenStations(stationFrom, stationTo);
    }

    @Override
    @Transactional
    public List<SectionEntity> getSectionsByRoute(List<StationEntity> route) {
        return sectionDao.getSectionsByRoute(route);
    }

    @Override
    @Transactional
    public void createSection(StationEntity station, int length, TrackEntity track) {
        sectionDao.createSection(station, length, track);
    }
}