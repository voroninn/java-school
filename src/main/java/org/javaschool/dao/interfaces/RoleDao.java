package org.javaschool.dao.interfaces;

import org.javaschool.entities.RoleEntity;

public interface RoleDao {

    RoleEntity findRoleByName(String name);
}
