package com.kosuri.rxkolan.service.impl;

import com.kosuri.rxkolan.constant.ErrorConstants;
import com.kosuri.rxkolan.entity.Role;
import com.kosuri.rxkolan.exception.BadRequestException;
import com.kosuri.rxkolan.model.user.role.RoleRequest;
import com.kosuri.rxkolan.repository.RoleRepository;
import com.kosuri.rxkolan.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    @Override
    public Role addNewRole(RoleRequest roleRequest) {
        log.info("Initiating Process to Add New Roles {}",roleRequest.getRoleName());
        Optional<Role> roleOptional = roleRepository.findByName(roleRequest.getRoleName());
        if(roleOptional.isPresent()){
            log.info("Role Already Present In Our System");
            throw  new BadRequestException(ErrorConstants.ROLE_ALREADY_EXISTS);
        }
        return  roleRepository.save(new Role(roleRequest.getRoleName()));
    }

    @Override
    public List<Role> fetchAllRolesInTheSystem() {
        log.info("Initiating Process to Fetch All Active Roles ");
        return  roleRepository.findByActiveTrue();
    }

    @Override
    public Role findRoleByName(@NotNull  String roleName) {
        log.info("Initiating Process to Fetch All Active Roles ");
        return roleRepository.findByName(roleName).orElse(null);
    }

    @Override
    public List<Role> findAllRoleByName(List<String> roleName) {
        return roleRepository.findByNameInAndActiveTrue(roleName);

    }
}
