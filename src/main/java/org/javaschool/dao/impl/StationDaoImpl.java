package org.javaschool.dao.impl;

import org.javaschool.dao.interfaces.StationDao;
import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TrainEntity;
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
    public StationEntity getStationByName(String name) {
        StationEntity station;
        try {
            station = (StationEntity) entityManager.createQuery("SELECT s FROM StationEntity s " +
                    "WHERE s.name = :name").setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            station = null;
        }
        return station;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<StationEntity> getAllStations() {
        Query query = entityManager.createQuery("SELECT s FROM StationEntity s ORDER BY s.name");
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

    @Override
    @SuppressWarnings("unchecked")
    public List<StationEntity> getStationsByTrain(TrainEntity train) {
        Query query = entityManager.createQuery("SELECT DISTINCT s.station FROM ScheduleEntity s WHERE s.train = :train");
        query.setParameter("train", train);
        return query.getResultList();
    }
}