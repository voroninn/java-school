package org.javaschool.dao.impl;

import org.javaschool.dao.interfaces.MappingDao;
import org.javaschool.entities.MappingEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TrackEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class MappingDaoImpl implements MappingDao {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MappingEntity getMapping(int id) {
        return entityManager.find(MappingEntity.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<MappingEntity> getAllMappings() {
        Query query = entityManager.createQuery("SELECT m FROM MappingEntity m");
        return query.getResultList();
    }

    @Override
    public void addMapping(MappingEntity mapping) {
        entityManager.persist(mapping);
    }

    @Override
    public void editMapping(MappingEntity mapping) {
        entityManager.merge(mapping);
    }

    @Override
    public void deleteMapping(MappingEntity mapping) {
        entityManager.remove(entityManager.merge(mapping));
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<StationEntity> getOrderedStationsByTrack(TrackEntity track) {
        Query query = entityManager.createQuery("SELECT m.station FROM MappingEntity m " +
                "WHERE m.track = :track ORDER BY m.stationOrder");
        query.setParameter("track", track);
        return query.getResultList();
    }
}