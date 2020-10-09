package org.javaschool.dao.impl;

import org.javaschool.dao.interfaces.UserDao;
import org.javaschool.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserEntity findUserByUsername(String username) {
        UserEntity user;
        try {
            user = (UserEntity) entityManager.createQuery("SELECT t FROM UserEntity t where t.username = :username")
                    .setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }
        return user;
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        UserEntity user;
        try {
            user = (UserEntity) entityManager.createQuery("SELECT t FROM UserEntity t where t.email = :email")
                    .setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }
        return user;
    }

    @Override
    public void addUser(UserEntity user) {
        entityManager.persist(user);
    }
}
