package org.javaschool.dao.interfaces;

import org.javaschool.entities.UserEntity;

public interface UserDao {

    UserEntity findUserByUsername(String username);

    UserEntity findUserByEmail(String email);

    void addUser(UserEntity user);
}