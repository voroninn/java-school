package org.javaschool.dao;

import org.javaschool.entities.RoleEntity;

public interface RoleDao {

    RoleEntity findRoleByName(String name);
}
