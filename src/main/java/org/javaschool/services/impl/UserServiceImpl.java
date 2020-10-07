package org.javaschool.services.impl;

import org.javaschool.dao.interfaces.RoleDao;
import org.javaschool.dao.interfaces.UserDao;
import org.javaschool.entities.UserEntity;
import org.javaschool.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void save(UserEntity user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(roleDao.findRoleByName("ROLE_USER")));
        userDao.addUser(user);
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }
}