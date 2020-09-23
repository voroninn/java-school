package org.javaschool.services;

import org.javaschool.dao.RoleDao;
import org.javaschool.dao.UserDao;
import org.javaschool.entities.RoleEntity;
import org.javaschool.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(UserEntity user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(new RoleEntity(1, "ROLE_USER")));
        userDao.addUser(user);
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }
}
