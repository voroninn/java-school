package org.javaschool.dao;

import org.javaschool.entities.StationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class StationDaoImpl implements StationDao {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public StationEntity getStation(int id) {
        return entityManager.find(StationEntity.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<StationEntity> getAllStations() {
        Query query = entityManager.createQuery("SELECT e FROM StationEntity e");
        return query.getResultList();
    }

    @Override
    public void addStation(StationEntity station) {
        entityManager.persist(station);
    }

    @Override
    public void editStation(StationEntity station) {
        entityManager.merge(station);
    }

    @Override
    public void deleteStation(StationEntity station) {
        entityManager.remove(entityManager.merge(station));
    }
}

