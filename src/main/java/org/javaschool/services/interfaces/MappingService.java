package org.javaschool.services.interfaces;

import org.javaschool.dto.MappingDto;
import org.javaschool.dto.StationDto;
import org.javaschool.dto.TrackDto;

import java.util.List;

public interface MappingService {

    MappingDto getMapping(int id);

    List<MappingDto> getAllMappings();

    List<MappingDto> getMappingsByTrack(TrackDto trackDto);

    void addMapping(MappingDto mappingDto);

    void editMapping(MappingDto mappingDto);

    void deleteMapping(MappingDto mappingDto);

    List<StationDto> getOrderedStationsByTrack(TrackDto trackDto);

    TrackDto getTrack(int id);

    void appendStation(StationDto stationDto, int trackId, String appendLocation);

    int getStationOrder(StationDto stationDto, TrackDto trackDto);

    StationDto getStationByOrder(TrackDto trackDto, int stationOrder);
}
