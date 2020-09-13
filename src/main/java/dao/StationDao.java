package dao;

import entities.StationEntity;

import javax.persistence.*;
import java.util.List;

public class StationDao {
    private static final EntityManagerFactory emFactoryObj =
            Persistence.createEntityManagerFactory("sbb-pu");

    private final EntityManager entityManager = emFactoryObj.createEntityManager();

    public StationEntity get(int id) {
        return entityManager.find(StationEntity.class, id);
    }

    public List<StationEntity> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM StationEntity e");
        return query.getResultList();
    }

    public void save(StationEntity station) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(station);
        transaction.commit();
    }

    public void delete(StationEntity station) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(station);
        transaction.commit();
    }
}

