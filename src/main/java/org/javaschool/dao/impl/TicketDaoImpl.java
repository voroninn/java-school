package org.javaschool.dao.impl;

import org.javaschool.dao.interfaces.TicketDao;
import org.javaschool.entities.TicketEntity;
import org.javaschool.entities.TrainEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TicketDaoImpl implements TicketDao {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TicketEntity getTicket(int id) {
        return entityManager.find(TicketEntity.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TicketEntity> getAllTickets() {
        Query query = entityManager.createQuery("SELECT t FROM TicketEntity t");
        return query.getResultList();
    }

    @Override
    public void addTicket(TicketEntity ticket) {
        entityManager.persist(ticket);
    }

    @Override
    public void editTicket(TicketEntity ticket) {
        entityManager.merge(ticket);
    }

    @Override
    public void deleteTicket(TicketEntity ticket) {
        entityManager.remove(entityManager.merge(ticket));
    }

    @Override
    public long getTicketCount() {
        Query query = entityManager.createQuery("SELECT count(t) FROM TicketEntity t");
        return (Long) query.getSingleResult();
    }
}
