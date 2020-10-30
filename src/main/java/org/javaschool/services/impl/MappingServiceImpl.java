package org.javaschool.services.impl;

import lombok.extern.log4j.Log4j2;
import org.javaschool.dao.interfaces.MappingDao;
import org.javaschool.dto.MappingDto;
import org.javaschool.dto.StationDto;
import org.javaschool.dto.TrackDto;
import org.javaschool.entities.MappingEntity;
import org.javaschool.mapper.MappingMapper;
import org.javaschool.mapper.StationMapper;
import org.javaschool.mapper.TrackMapper;
import org.javaschool.services.interfaces.MappingService;
import org.javaschool.services.interfaces.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class MappingServiceImpl implements MappingService {

    @Autowired
    private MappingDao mappingDao;

    @Autowired
    private TrackService trackService;

    @Autowired
    private MappingMapper mappingMapper;

    @Autowired
    private TrackMapper trackMapper;

    @Autowired
    private StationMapper stationMapper;

    @Override
    @Transactional
    public MappingDto getMapping(int id) {
        return mappingMapper.toDto(mappingDao.getMapping(id));
    }

    @Override
    @Transactional
    public List<MappingDto> getAllMappings() {
        return mappingMapper.toDtoList(mappingDao.getAllMappings());
    }

    @Override
    @Transactional
    public List<MappingDto> getMappingsByTrack(TrackDto trackDto) {
        return mappingMapper.toDtoList(mappingDao.getMappingsByTrack(trackMapper.toEntity(trackDto)));
    }

    @Override
    @Transactional
    public void addMapping(MappingDto mappingDto) {
        mappingDao.addMapping(mappingMapper.toEntity(mappingDto));
        log.info("Created new mapping " + mappingDto.getId());
    }

    @Override
    @Transactional
    public void editMapping(MappingDto mappingDto) {
        mappingDao.editMapping(mappingMapper.toEntity(mappingDto));
        log.info("Edited mapping " + mappingDto.getId());
    }

    @Override
    @Transactional
    public void deleteMapping(MappingDto mappingDto) {
        List<MappingDto> mappingDtoList = getMappingsByTrack(mappingDto.getTrack());
        for (int i = mappingDto.getStationOrder(); i < mappingDtoList.size(); i++) {
            mappingDtoList.get(i).setStationOrder(i);
            mappingDao.editMapping(mappingMapper.toEntity(mappingDtoList.get(i)));
        }
        mappingDao.deleteMapping(mappingMapper.toEntity(mappingDto));
        log.info("Deleted mapping " + mappingDto.getId());
    }

    @Override
    @Transactional
    public List<StationDto> getOrderedStationsByTrack(TrackDto trackDto) {
        return stationMapper.toDtoList(mappingDao.getOrderedStationsByTrack(trackMapper.toEntity(trackDto)));
    }

    @Override
    @Transactional
    public TrackDto getTrack(int id) {
        return trackMapper.toDto(mappingDao.getTrack(id));
    }

    @Override
    @Transactional
    public void appendStation(StationDto stationDto, int trackId, String appendLocation) {
        TrackDto trackDto = trackService.getTrack(trackId);
        int stationOrder;
        switch (appendLocation) {
            case "before":
                stationOrder = 1;
                List<MappingDto> mappingDtoList = getMappingsByTrack(trackDto);
                if (mappingDtoList.get(0).getStationOrder() == 1) {
                    for (MappingDto mappingDto : mappingDtoList) {
                        mappingDto.setStationOrder(mappingDto.getStationOrder() + 1);
                        mappingDao.editMapping(mappingMapper.toEntity(mappingDto));
                    }
                }
                mappingDao.addMapping(new MappingEntity(stationMapper.toEntity(stationDto),
                        trackMapper.toEntity(trackDto), stationOrder));
                break;
            case "after":
                stationOrder = getOrderedStationsByTrack(trackDto).size() + 1;
                mappingDao.addMapping(new MappingEntity(stationMapper.toEntity(stationDto),
                        trackMapper.toEntity(trackDto), stationOrder));
                break;
        }
    }

    @Override
    @Transactional
    public int getStationOrder(StationDto stationDto, TrackDto trackDto) {
        return mappingDao.getStationOrder(stationMapper.toEntity(stationDto), trackMapper.toEntity(trackDto));
    }

    @Override
    @Transactional
    public StationDto getStationByOrder(TrackDto trackDto, int stationOrder) {
        return stationMapper.toDto(mappingDao.getStationByOrder(trackMapper.toEntity(trackDto), stationOrder));
    }
}