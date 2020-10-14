package org.javaschool.dao.interfaces;

import org.javaschool.entities.TrackEntity;

import java.util.List;

public interface TrackDao {

    TrackEntity getTrack(int id);

    List<TrackEntity> getAllTracks();

    void addTrack(TrackEntity track);

    void editTrack(TrackEntity track);

    void deleteTrack(TrackEntity track);
}
