package dao;

import entities.TicketEntity;

import javax.persistence.*;
import java.util.List;

public class TicketDao {
    private static final EntityManagerFactory emFactoryObj =
            Persistence.createEntityManagerFactory("sbb-pu");

    private final EntityManager entityManager = emFactoryObj.createEntityManager();

    public TicketEntity get(int id) {
        return entityManager.find(TicketEntity.class, id);
    }

    public List<TicketEntity> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM TicketEntity e");
        return query.getResultList();
    }

    public void save(TicketEntity ticket) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(ticket);
        transaction.commit();
    }

    public void delete(TicketEntity ticket) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(ticket);
        transaction.commit();
    }
}
