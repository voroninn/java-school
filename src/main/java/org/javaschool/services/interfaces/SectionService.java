package org.javaschool.services.interfaces;

import org.javaschool.dto.SectionDto;
import org.javaschool.dto.StationDto;
import org.javaschool.dto.TrackDto;

import java.util.List;

public interface SectionService {

    SectionDto getSection(int id);

    List<SectionDto> getAllSections();

    void addSection(SectionDto sectionDto);

    void editSection(SectionDto sectionDto);

    void deleteSection(SectionDto sectionDto);

    SectionDto getSectionBetweenStations(StationDto stationFromDto, StationDto stationToDto);

    List<SectionDto> getSectionsByRoute(List<StationDto> route);

    void createSection(StationDto stationDto, int length, TrackDto trackDto);
}