package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private RoleRepo roleRepo;

    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    @Transactional
    public void save(Role role){
        roleRepo.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    @Override
    public Role getOneRole(Long id) {
        Optional<Role> foundOneRole = roleRepo.findById(id);
        return foundOneRole.orElse(null);
    }

    @Override
    public List<Role> getRoles(Long[] roleId) {

        List<Role> roleResult = new ArrayList<>();

        if (roleId == null) {
            roleResult.add(roleRepo.findAll().get(0));
        } else {
            for (long i : roleId) {
                List<Role> roles = roleRepo.findAll();
                roleResult.add(roles.stream().filter(r -> r.getId() == i).findAny().orElse(null));
            }
        }

        return roleResult;
    }
}
