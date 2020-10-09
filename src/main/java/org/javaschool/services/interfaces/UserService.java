package org.javaschool.services.interfaces;

import org.javaschool.entities.UserEntity;

public interface UserService {

    void save(UserEntity user);

    UserEntity findUserByUsername(String username);

    UserEntity findUserByEmail(String email);

    String getCurrentUserName();
}
