package org.javaschool.services.interfaces;

import org.javaschool.dto.UserDto;

public interface UserService {

    void save(UserDto userDto);

    UserDto findUserByUsername(String username);

    UserDto findUserByEmail(String email);

    String getCurrentUserName();
}