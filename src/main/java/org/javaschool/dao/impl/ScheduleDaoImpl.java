package org.javaschool.dao.impl;

import org.javaschool.dao.interfaces.ScheduleDao;
import org.javaschool.entities.ScheduleEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.entities.TrainEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ScheduleDaoImpl implements ScheduleDao {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ScheduleEntity getSchedule(int id) {
        return entityManager.find(ScheduleEntity.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<ScheduleEntity> getAllSchedules() {
        Query query = entityManager.createQuery("SELECT s FROM ScheduleEntity s");
        return query.getResultList();
    }

    @Override
    public void addSchedule(ScheduleEntity schedule) {
        entityManager.persist(entityManager.merge(schedule));
    }

    @Override
    public void editSchedule(ScheduleEntity schedule) {
        entityManager.merge(schedule);
    }

    @Override
    public void deleteSchedule(ScheduleEntity schedule) {
        entityManager.remove(entityManager.merge(schedule));
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<ScheduleEntity> getSchedulesByStationAndDirection(StationEntity station, boolean direction) {
        Query query = entityManager.createQuery("SELECT s FROM ScheduleEntity s " +
                "WHERE s.station.name = :stationName AND s.direction = :direction");
        query.setParameter("stationName", station.getName());
        query.setParameter("direction", direction);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<ScheduleEntity> getSchedulesByTrain(TrainEntity train) {
        Query query = entityManager.createQuery("SELECT s FROM ScheduleEntity s WHERE s.train = :train");
        query.setParameter("train", train);
        return query.getResultList();
    }
}