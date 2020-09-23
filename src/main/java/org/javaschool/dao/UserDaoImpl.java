package org.javaschool.dao;

import org.javaschool.entities.PassengerEntity;
import org.javaschool.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserEntity findUserByUsername(String username) {
        return entityManager.find(UserEntity.class, username);
    }

    @Override
    public void addUser(UserEntity user) {
        //EntityTransaction transaction = entityManager.getTransaction();
        entityManager.persist(user);
    }
}
