package org.javaschool.dao.interfaces;

import org.javaschool.entities.UserEntity;

public interface UserDao {

    UserEntity findUserByUsername(String username);

    void addUser(UserEntity user);
}
