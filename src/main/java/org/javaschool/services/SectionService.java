package org.javaschool.services;

import org.javaschool.entities.SectionEntity;

import java.util.List;

public interface SectionService {

    SectionEntity getSection(int id);

    List<SectionEntity> getAllSections();

    void addSection(SectionEntity section);

    void editSection(SectionEntity section);

    void deleteSection(SectionEntity section);
}
