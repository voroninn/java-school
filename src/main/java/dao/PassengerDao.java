package dao;

import entities.PassengerEntity;

import javax.persistence.*;
import java.util.List;

public class PassengerDao {
    private static final EntityManagerFactory emFactoryObj =
                Persistence.createEntityManagerFactory("sbb-pu");

    private final EntityManager entityManager = emFactoryObj.createEntityManager();

    public PassengerEntity get(int id) {
        return entityManager.find(PassengerEntity.class, id);
    }

    public List<PassengerEntity> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM PassengerEntity e");
        return query.getResultList();
    }

    public void save(PassengerEntity passenger) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(passenger);
        transaction.commit();
    }

    public void delete(PassengerEntity passenger) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(passenger);
            transaction.commit();
    }
}
