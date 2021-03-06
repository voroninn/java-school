package org.javaschool.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.javaschool.dao.interfaces.StationDao;
import org.javaschool.dto.SectionDto;
import org.javaschool.dto.StationDto;
import org.javaschool.dto.TrainDto;
import org.javaschool.exception.IllegalOperationException;
import org.javaschool.mapper.StationMapper;
import org.javaschool.mapper.TrainMapper;
import org.javaschool.services.interfaces.MessagingService;
import org.javaschool.services.interfaces.PathFinderService;
import org.javaschool.services.interfaces.SectionService;
import org.javaschool.services.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StationServiceImpl implements StationService {

    private final StationDao stationDao;
    private final PathFinderService pathFinderService;
    private final SectionService sectionService;
    private final MessagingService messagingService;
    private final StationMapper stationMapper;
    private final TrainMapper trainMapper;

    @Override
    public StationDto getStation(int id) {
        return stationMapper.toDto(stationDao.getStation(id));
    }

    @Override
    public StationDto getStationByName(String name) {
        return stationMapper.toDto(stationDao.getStationByName(name));
    }

    @Override
    public List<StationDto> getAllStations() {
        return stationMapper.toDtoList(stationDao.getAllStations());
    }

    @Override
    @Transactional
    public void addStation(StationDto stationDto) {
        stationDao.addStation(stationMapper.toEntity(stationDto));
        log.info("Created new station " + stationDto.getName());
    }

    @Override
    @Transactional
    public void editStation(StationDto stationDto) {
        stationDao.editStation(stationMapper.toEntity(stationDto));
        log.info("Edited station " + stationDto.getName());
    }

    @Override
    @Transactional
    public void deleteStation(StationDto stationDto) {
        if (stationDto.getId() <= 18) {
            throw new IllegalOperationException("Attempted to delete a core station.");
        }
        stationDao.deleteStation(stationMapper.toEntity(stationDto));
        log.info("Deleted station " + stationDto.getName());
        messagingService.sendMessage();
    }

    @Override
    public LinkedList<StationDto> getRoute(String stationFrom, String stationTo) {
        pathFinderService.initialize(getStationByName(stationFrom));
        return pathFinderService.createRoute(getStationByName(stationTo));
    }

    @Override
    public int countTrackChanges(LinkedList<StationDto> route) {
        setEndpoints(route);
        int counter = 0;
        if (route.size() > 2) {
            for (int i = 0; i < route.size() - 2; i++) {
                SectionDto section1 = sectionService.getSectionBetweenStations(route.get(i), route.get(i + 1));
                SectionDto section2 = sectionService.getSectionBetweenStations(route.get(i + 1), route.get(i + 2));
                if (!section1.getTrack().equals(section2.getTrack())) {
                    route.get(i + 1).setBreakpoint(true);
                    route.get(i + 1).setEndpoint(false);
                    counter++;
                }
            }
        }
        return counter;
    }

    @Override
    public List<StationDto> getStationsByTrain(TrainDto trainDto) {
        return stationMapper.toDtoList(stationDao.getStationsByTrain(trainMapper.toEntity(trainDto)));
    }

    @Override
    public List<StationDto> selectEndpoints(List<StationDto> stationDtoList) {
        List<StationDto> endpoints = new ArrayList<>();
        endpoints.add(stationDtoList.get(0));
        endpoints.add(stationDtoList.get(stationDtoList.size() - 1));
        return endpoints;
    }

    @Override
    public void setEndpoints(LinkedList<StationDto> route) {
        route.get(0).setEndpoint(true);
        route.get(route.size() - 1).setEndpoint(true);
    }

    @Override
    public StationDto updateStationDto(StationDto stationDto) {
        return getStationByName(stationDto.getName());
    }
}