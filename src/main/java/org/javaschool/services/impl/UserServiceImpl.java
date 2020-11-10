package org.javaschool.services.impl;

import lombok.extern.log4j.Log4j2;
import org.javaschool.dao.interfaces.RoleDao;
import org.javaschool.dao.interfaces.UserDao;
import org.javaschool.dto.UserDto;
import org.javaschool.entities.UserEntity;
import org.javaschool.mapper.UserMapper;
import org.javaschool.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public void save(UserDto userDto) {
        UserEntity user = userMapper.toEntity(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(roleDao.findRoleByName("ROLE_USER")));
        userDao.addUser(user);
        log.info("Created new user " + user.getUsername());
    }

    @Override
    public UserDto findUserByUsername(String username) {
        return userMapper.toDto(userDao.findUserByUsername(username));
    }

    @Override
    public UserDto findUserByEmail(String email) {
        return userMapper.toDto(userDao.findUserByEmail(email));
    }

    @Override
    public String getCurrentUserName() {
        String currentUserName = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        return currentUserName;
    }
}