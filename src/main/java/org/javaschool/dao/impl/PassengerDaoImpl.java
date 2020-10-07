package org.javaschool.dao.impl;

import org.javaschool.dao.interfaces.PassengerDao;
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
    @SuppressWarnings("unchecked")
    public List<PassengerEntity> getAllPassengers() {
        Query query = entityManager.createQuery("SELECT e FROM PassengerEntity e", PassengerEntity.class);
        return query.getResultList();
    }

    @Override
    public void addPassenger(PassengerEntity passenger) {
        entityManager.persist(passenger);
    }

    @Override
    public void editPassenger(PassengerEntity passenger) {
        entityManager.merge(passenger);
    }

    @Override
    public void deletePassenger(PassengerEntity passenger) {
            entityManager.remove(entityManager.merge(passenger));
    }
}