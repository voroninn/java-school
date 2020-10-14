package org.javaschool.services.interfaces;

import org.javaschool.entities.MappingEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TrackEntity;

import java.util.List;

public interface MappingService {

    MappingEntity getMapping(int id);

    List<MappingEntity> getAllMappings();

    List<MappingEntity> getMappingsByTrack(TrackEntity track);

    void addMapping(MappingEntity mapping);

    void editMapping(MappingEntity mapping);

    void deleteMapping(MappingEntity mapping);

    List<StationEntity> getOrderedStationsByTrack(TrackEntity track);

    TrackEntity getTrack(int id);

    void appendStation(StationEntity station, int trackId, String appendLocation);

    int getStationOrder(StationEntity station, TrackEntity track);

    StationEntity getStationByOrder(TrackEntity track, int stationOrder);
}
