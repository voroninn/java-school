package org.javaschool.services.impl;

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

    @Override
    @Transactional
    public SectionEntity getSection(int id) {
        return sectionDao.getSection(id);
    }

    @Override
    @Transactional
    public List<SectionEntity> getAllSections() {
        return sectionDao.getAllSections();
    }

    @Override
    @Transactional
    public void addSection(SectionEntity section) {
        sectionDao.addSection(section);
    }

    @Override
    @Transactional
    public void editSection(SectionEntity section) {
        sectionDao.editSection(section);
    }

    @Override
    @Transactional
    public void deleteSection(SectionEntity section) {
        sectionDao.deleteSection(section);
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