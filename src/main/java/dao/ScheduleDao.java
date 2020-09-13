package dao;

import entities.ScheduleEntity;

import javax.persistence.*;
import java.util.List;

public class ScheduleDao {
    private static final EntityManagerFactory emFactoryObj =
            Persistence.createEntityManagerFactory("sbb-pu");

    private final EntityManager entityManager = emFactoryObj.createEntityManager();

    public ScheduleEntity get(int id) {
        return entityManager.find(ScheduleEntity.class, id);
    }

    public List<ScheduleEntity> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM ScheduleEntity e");
        return query.getResultList();
    }

    public void save(ScheduleEntity schedule) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(schedule);
        transaction.commit();
    }

    public void delete(ScheduleEntity schedule) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(schedule);
        transaction.commit();
    }
}
