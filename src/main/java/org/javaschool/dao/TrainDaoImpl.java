package org.javaschool.dao;

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
    public List<TrainEntity> getAllTrains() {
        Query query = entityManager.createQuery("SELECT e FROM TrainEntity e");
        return query.getResultList();
    }

    @Override
    public void addTrain(TrainEntity train) {
        entityManager.persist(train);
    }

    @Override
    public void updateTrain(TrainEntity train) {
        entityManager.merge(train);
    }

    @Override
    public void deleteTrain(TrainEntity train) {
        entityManager.remove(entityManager.merge(train));
    }
}
