package org.javaschool.dao;

import org.javaschool.entities.SectionEntity;

import java.util.List;

public interface SectionDao {

    SectionEntity getSection(int id);

    List<SectionEntity> getAllSections();

    void addSection(SectionEntity section);

    void editSection(SectionEntity section);

    void deleteSection(SectionEntity section);
}