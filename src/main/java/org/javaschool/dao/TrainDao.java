package org.javaschool.dao;

import org.javaschool.entities.TrainEntity;

import javax.persistence.*;
import java.util.List;

public class TrainDao {
    private static final EntityManagerFactory emFactoryObj =
            Persistence.createEntityManagerFactory("sbb-pu");

    private final EntityManager entityManager = emFactoryObj.createEntityManager();

    public TrainEntity get(int id) {
        return entityManager.find(TrainEntity.class, id);
    }

    public List<TrainEntity> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM TrainEntity e");
        return query.getResultList();
    }

    public void save(TrainEntity train) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(train);
        transaction.commit();
    }

    public void delete(TrainEntity train) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(train);
        transaction.commit();
    }
}
