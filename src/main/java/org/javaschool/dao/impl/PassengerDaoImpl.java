package org.javaschool.dao.impl;

import org.javaschool.dao.interfaces.PassengerDao;
import org.javaschool.entities.PassengerEntity;
import org.javaschool.entities.TrainEntity;
import org.javaschool.entities.UserEntity;
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
        Query query = entityManager.createQuery("SELECT p FROM PassengerEntity p", PassengerEntity.class);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PassengerEntity> getPassengersByTrain(TrainEntity train) {
        Query query = entityManager.createQuery("SELECT DISTINCT t.passenger FROM TicketEntity t " +
                "WHERE :train member of t.trains AND t.date <= CURRENT_DATE");
        query.setParameter("train", train);
        return query.getResultList();
    }

    @Override
    public void addPassenger(PassengerEntity passenger) {
        entityManager.persist(entityManager.merge(passenger));
    }

    @Override
    public void editPassenger(PassengerEntity passenger) {
        entityManager.merge(passenger);
    }

    @Override
    public void deletePassenger(PassengerEntity passenger) {
        entityManager.remove(entityManager.merge(passenger));
    }

    @Override
    public PassengerEntity getPassengerByUser(UserEntity user) {
        PassengerEntity passenger = null;
        try {
            Query query = entityManager.createQuery("SELECT p FROM PassengerEntity p where p.user = :user");
            query.setParameter("user", user);
            passenger = (PassengerEntity) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        return passenger;
    }

    @Override
    public PassengerEntity getPassengerByPassportNumber(int passportNumber) {
        PassengerEntity passenger = null;
        try {
            Query query = entityManager.createQuery("SELECT p FROM PassengerEntity p where p.passportNumber = :passportNumber");
            query.setParameter("passportNumber", passportNumber);
            passenger = (PassengerEntity) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        return passenger;
    }
}