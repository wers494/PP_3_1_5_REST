package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class SetupUsers {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public SetupUsers(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    private Role roleAdmin  = new Role("ROLE_ADMIN");
    private Role roleUser  = new Role("ROLE_USER");

    public List<Role> setAdminRole() {
        List<Role> adminRole = new ArrayList<>();
        adminRole.add(roleAdmin);
        return adminRole;
    }

    public List<Role> setRoleUser() {
        List<Role> userRole = new ArrayList<>();
        userRole.add(roleUser);
        return userRole;
    }

    @Transactional
    @Bean
    public void addUser() {
        roleService.save(roleAdmin);
        roleService.save(roleUser);

        User admin = new User("admin", "$2a$12$x2jGJqzzWh7mp1c4bNW/MePnpkb5Q.garsy0PN9cmK3Ja0UQ3N432",
                "admin@mail.ru", "adminName", "adminSurname", 26, setAdminRole()); // пароль: admin
        User user = new User("user", "$2a$12$AyaqSH0/6oYd6yBC2sKfgutia.m2Cz//roNJ0scMTDYmBEba8.87q",
                "user@mail.ru", "userName", "userSurname", 25, setRoleUser()); // пароль: user

        userService.save(admin);
        userService.save(user);

    }

}
