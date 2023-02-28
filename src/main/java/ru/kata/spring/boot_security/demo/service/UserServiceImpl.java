package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.kata.spring.boot_security.demo.configs.WebSecurityConfig.passwordEncoder;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    @Transactional
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public User getOneUser(Long id) {
        Optional<User> foundUser = userRepo.findById(id);
        return foundUser.orElse(null);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        user.setId(user.getId());
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public User findByUsername(String username){
        return userRepo.findByUsername(username);
    }

    @Transactional
    @Override
    public void createNewUser(User user, List<Role> roles) {
        user.setRoles(roles);
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        save(user);
    }

    @Transactional
    @Override
    public void editUser(User user, List<Role> roles) {
        user.setRoles(roles);
        updateUser(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> roles(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());
    }
}
