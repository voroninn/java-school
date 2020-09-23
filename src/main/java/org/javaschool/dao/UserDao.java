package org.javaschool.dao;

import org.javaschool.entities.UserEntity;

public interface UserDao {
    UserEntity findUserByUsername(String username);
    void addUser(UserEntity user);
}
