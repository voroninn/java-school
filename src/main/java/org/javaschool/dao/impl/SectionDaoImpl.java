package org.javaschool.dao.impl;

import org.javaschool.dao.interfaces.SectionDao;
import org.javaschool.entities.SectionEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.services.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SectionDaoImpl implements SectionDao {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private StationService stationService;

    @Override
    public SectionEntity getSection(int id) {
        return entityManager.find(SectionEntity.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<SectionEntity> getAllSections() {
        Query query = entityManager.createQuery("SELECT s FROM SectionEntity s");
        return query.getResultList();
    }

    @Override
    public void addSection(SectionEntity section) {
        entityManager.persist(section);
    }

    @Override
    public void editSection(SectionEntity section) {
        entityManager.merge(section);
    }

    @Override
    public void deleteSection(SectionEntity section) {
        entityManager.remove(entityManager.merge(section));
    }

    @Override
    public SectionEntity getSectionBetweenStations(StationEntity stationFrom, StationEntity stationTo) {
        Query query = entityManager.createQuery("SELECT s FROM SectionEntity s " +
                "WHERE s.stationFrom = :stationFrom AND s.stationTo = :stationTo");
        query.setParameter("stationFrom", stationFrom);
        query.setParameter("stationTo", stationTo);
        return (SectionEntity) query.getSingleResult();
    }

    @Override
    public List<SectionEntity> getSectionsByRoute(List<StationEntity> route) {
        List<SectionEntity> sectionsByRoute = new ArrayList<>();
        for (int i = 0; i < route.size() - 1; i++) {
            sectionsByRoute.add(getSectionBetweenStations(route.get(i), route.get(i + 1)));
        }
        return sectionsByRoute;
    }
}