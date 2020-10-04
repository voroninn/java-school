package org.javaschool.dao;

import org.javaschool.entities.SectionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class SectionDaoImpl implements SectionDao {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SectionEntity getSection(int id) {
        return entityManager.find(SectionEntity.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<SectionEntity> getAllSections() {
        Query query = entityManager.createQuery("SELECT s FROM SectionEntity s");
        return query.getResultList();
    }

    @Override
    public void addSection(SectionEntity section) {
        entityManager.persist(section);
    }

    @Override
    public void editSection(SectionEntity section) {
        entityManager.merge(section);
    }

    @Override
    public void deleteSection(SectionEntity section) {
        entityManager.remove(entityManager.merge(section));
    }
}