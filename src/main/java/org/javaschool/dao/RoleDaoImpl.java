package org.javaschool.dao;

import org.javaschool.entities.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class RoleDaoImpl implements RoleDao {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public RoleEntity findRoleByName(String name) {
        RoleEntity role;
        try {
            role = (RoleEntity) entityManager.createQuery("SELECT t FROM RoleEntity t where t.name = :name")
                    .setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            role = null;
        }
        return role;
    }
}
