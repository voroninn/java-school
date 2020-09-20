package org.javaschool.dao;

import org.javaschool.entities.PassengerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class PassengerDaoImpl implements PassengerDao {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    public PassengerEntity getPassenger(int id) {
        return entityManager.find(PassengerEntity.class, id);
    }

    @Override
    public List<PassengerEntity> getAllPassengers() {
        Query query = entityManager.createQuery("SELECT e FROM PassengerEntity e", PassengerEntity.class);
        return query.getResultList();
    }

    @Override
    public void addPassenger(PassengerEntity passenger) {
        //EntityTransaction transaction = entityManager.getTransaction();
        entityManager.persist(passenger);
    }

    @Override
    public void updatePassenger(PassengerEntity passenger) {
        //EntityTransaction transaction = entityManager.getTransaction();
        entityManager.merge(passenger);
    }

    @Override
    public void deletePassenger(PassengerEntity passenger) {
            entityManager.remove(entityManager.merge(passenger));
    }
}