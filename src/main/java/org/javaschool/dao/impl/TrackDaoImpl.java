package org.javaschool.dao.impl;

import org.javaschool.dao.interfaces.TrackDao;
import org.javaschool.entities.TrackEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TrackDaoImpl implements TrackDao {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TrackEntity getTrack(int id) {
        return entityManager.find(TrackEntity.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<TrackEntity> getAllTracks() {
        Query query = entityManager.createQuery("SELECT t FROM TrackEntity t");
        return query.getResultList();
    }

    @Override
    public void addTrack(TrackEntity track) {
        entityManager.persist(track);
    }

    @Override
    public void editTrack(TrackEntity track) {
        entityManager.merge(track);
    }

    @Override
    public void deleteTrack(TrackEntity track) {
        entityManager.remove(entityManager.merge(track));
    }
}
