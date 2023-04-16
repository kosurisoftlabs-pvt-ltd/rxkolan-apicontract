package com.kosuri.rxkolan.controller;

import com.kosuri.rxkolan.entity.Role;
import com.kosuri.rxkolan.model.user.role.RoleRequest;
import com.kosuri.rxkolan.service.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.kosuri.rxkolan.constant.Constants.BEARER_KEY;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/role")
@SecurityRequirement(name = BEARER_KEY)
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> createNewRole(@RequestBody @Validated RoleRequest roleRequest) {
        Role response = roleService.addNewRole(roleRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Role>> fetchAllRoles() {
        return ResponseEntity.ok(roleService.fetchAllRolesInTheSystem());
    }
}
