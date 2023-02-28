package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showAllUsers(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("allUser", userService.getAllUsers());
        model.addAttribute("roleUser", roleService.getAllRoles());
        return "admin";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model, @ModelAttribute("roles") Role role) {
        model.addAttribute("roleUser", roleService.getAllRoles());
        return "admin";
    }

    @PostMapping()
    public String createNewUser(@ModelAttribute("user") User user, @RequestParam(name = "role", defaultValue = "0") Long[] id) {
        List<Role> roles = new ArrayList<>(roleService.getRoles(id));
        userService.createNewUser(user,roles);
        return "redirect:/admin";
    }

    @PatchMapping("/edit/{id}")
    public String editUser(@ModelAttribute("user") User user, @RequestParam(name = "role", defaultValue = "0") Long[] id) {
        List<Role> roles = new ArrayList<>(roleService.getRoles(id));
        userService.editUser(user, roles);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
