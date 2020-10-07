package org.javaschool.dao;

import org.javaschool.entities.ScheduleEntity;
import org.javaschool.entities.SectionEntity;
import org.javaschool.entities.StationEntity;
import org.javaschool.services.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScheduleDaoImpl implements ScheduleDao {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SectionService sectionService;

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
        entityManager.persist(schedule);
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
                "WHERE s.station = :station AND s.direction = :direction");
        query.setParameter("station", station);
        query.setParameter("direction", direction);
        return query.getResultList();
    }

    @Override
    public List<ScheduleEntity> getSchedulesByRoute(List<StationEntity> route) {
        List<ScheduleEntity> schedulesByRoute = new ArrayList<>();
        List<SectionEntity> sectionsByRoute = sectionService.getSectionsByRoute(route);
        for (SectionEntity section : sectionsByRoute) {
            schedulesByRoute.addAll(getSchedulesByStationAndDirection(section.getStationFrom(), section.isDirection()));
        }
        SectionEntity lastSection = sectionsByRoute.get(sectionsByRoute.size() - 1);
        schedulesByRoute.addAll(getSchedulesByStationAndDirection(lastSection.getStationTo(), lastSection.isDirection()));
        return schedulesByRoute;
    }
}
