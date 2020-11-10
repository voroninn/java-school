package org.javaschool.services.impl;

import lombok.extern.log4j.Log4j2;
import org.javaschool.dao.interfaces.SectionDao;
import org.javaschool.dto.SectionDto;
import org.javaschool.dto.StationDto;
import org.javaschool.dto.TrackDto;
import org.javaschool.entities.SectionEntity;
import org.javaschool.mapper.SectionMapper;
import org.javaschool.mapper.StationMapper;
import org.javaschool.mapper.TrackMapper;
import org.javaschool.services.interfaces.MappingService;
import org.javaschool.services.interfaces.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private MappingService mappingService;

    @Autowired
    private SectionMapper sectionMapper;

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private TrackMapper trackMapper;

    @Override
    public SectionDto getSection(int id) {
        return sectionMapper.toDto(sectionDao.getSection(id));
    }

    @Override
    public List<SectionDto> getAllSections() {
        return sectionMapper.toDtoList(sectionDao.getAllSections());
    }

    @Override
    @Transactional
    public void addSection(SectionDto sectionDto) {
        sectionDao.addSection(sectionMapper.toEntity(sectionDto));
        log.info("Created new section between " + sectionDto.getStationFrom() + " and " + sectionDto.getStationTo());
    }

    @Override
    @Transactional
    public void editSection(SectionDto sectionDto) {
        sectionDao.editSection(sectionMapper.toEntity(sectionDto));
        log.info("Edited section between " + sectionDto.getStationFrom() + " and " + sectionDto.getStationTo());
    }

    @Override
    @Transactional
    public void deleteSection(SectionDto sectionDto) {
        sectionDao.deleteSection(sectionMapper.toEntity(sectionDto));
        log.info("Deleted section between " + sectionDto.getStationFrom() + " and " + sectionDto.getStationTo());
    }

    @Override
    public SectionDto getSectionBetweenStations(StationDto stationFrom, StationDto stationTo) {
        return sectionMapper.toDto(sectionDao.getSectionBetweenStations(stationMapper.toEntity(stationFrom),
                stationMapper.toEntity(stationTo)));
    }

    @Override
    public List<SectionDto> getSectionsByRoute(List<StationDto> route) {
        return sectionMapper.toDtoList(sectionDao.getSectionsByRoute(stationMapper.toEntityList(route)));
    }

    @Override
    @Transactional
    public void createSection(StationDto stationDto, int length, TrackDto trackDto) {
        StationDto nearestStation;
        int stationOrder = mappingService.getStationOrder(stationDto, trackDto);
        if (stationOrder == 1) {
            nearestStation = mappingService.getStationByOrder(trackDto, 2);
            sectionDao.addSection(new SectionEntity(stationMapper.toEntity(stationDto),
                    stationMapper.toEntity(nearestStation), length, trackMapper.toEntity(trackDto), true));
            sectionDao.addSection(new SectionEntity(stationMapper.toEntity(stationDto),
                    stationMapper.toEntity(nearestStation), length, trackMapper.toEntity(trackDto), false));
        } else {
            nearestStation = mappingService.getStationByOrder(trackDto, stationOrder - 1);
            sectionDao.addSection(new SectionEntity(stationMapper.toEntity(stationDto),
                    stationMapper.toEntity(nearestStation), length, trackMapper.toEntity(trackDto), false));
            sectionDao.addSection(new SectionEntity(stationMapper.toEntity(stationDto),
                    stationMapper.toEntity(nearestStation), length, trackMapper.toEntity(trackDto), true));
        }
    }
}