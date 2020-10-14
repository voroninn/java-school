package org.javaschool.dao.impl;

import org.javaschool.dao.interfaces.TrainDao;
import org.javaschool.entities.TrackEntity;
import org.javaschool.entities.TrainEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class TrainDaoImpl implements TrainDao {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TrainEntity getTrain(int id) {
        return entityManager.find(TrainEntity.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TrainEntity> getAllTrains() {
        Query query = entityManager.createQuery("SELECT t FROM TrainEntity t ORDER BY t.track.id, t.name");
        return query.getResultList();
    }

    @Override
    public void addTrain(TrainEntity train) {
        entityManager.persist(train);
    }

    @Override
    public void editTrain(TrainEntity train) {
        entityManager.merge(train);
    }

    @Override
    public void deleteTrain(TrainEntity train) {
        entityManager.remove(entityManager.merge(train));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TrainEntity> getTrainsByTrack(TrackEntity track) {
        Query query = entityManager.createQuery("SELECT t FROM TrainEntity t WHERE t.track = :track");
        query.setParameter("track", track);
        return query.getResultList();
    }
}
