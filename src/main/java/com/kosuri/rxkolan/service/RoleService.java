package com.kosuri.rxkolan.service;

import com.kosuri.rxkolan.entity.Role;
import com.kosuri.rxkolan.model.user.role.RoleRequest;

import java.util.List;

public interface RoleService {

     Role addNewRole(RoleRequest roleRequest);

    List<Role> fetchAllRolesInTheSystem();

    Role findRoleByName(String roleName);

    List<Role> findAllRoleByName(List<String> roleName);
}
