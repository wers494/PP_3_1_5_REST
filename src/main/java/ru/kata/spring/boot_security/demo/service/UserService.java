package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    void save(User user);

    User getOneUser(Long id);

    void updateUser(User user);

    void deleteUser(Long id);

    User findByUsername(String username);
    void createNewUser(User user, List<Role> roles);
    void editUser(User user, List<Role> roles);
}
