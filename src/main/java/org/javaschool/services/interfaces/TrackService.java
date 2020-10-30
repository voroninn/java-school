package org.javaschool.services.interfaces;

import org.javaschool.dto.TrackDto;

import java.util.List;

public interface TrackService {

    TrackDto getTrack(int id);

    List<TrackDto> getAllTracks();

    void addTrack(TrackDto trackDto);

    void editTrack(TrackDto trackDto);

    void deleteTrack(TrackDto trackDto);
}