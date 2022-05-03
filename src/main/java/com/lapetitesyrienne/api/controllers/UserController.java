package com.lapetitesyrienne.api.controllers;

import java.util.List;

import com.lapetitesyrienne.api.exceptions.UserNotFoundException;
import com.lapetitesyrienne.api.models.User;
import com.lapetitesyrienne.api.repository.UserRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    List<User> all() {
        return repository.findAll();
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable String id) {
        return repository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable String id) {
        return repository.findById(id)
            .map(user -> {
                user.setFirstName(newUser.getFirstName());
                user.setLastName(newUser.getLastName());
                user.setRole(newUser.getRole());
                user.setDateOfBirth(newUser.getDateOfBirth());
                user.setEmail(newUser.getEmail());
                user.setPassword(newUser.getPassword());
                user.setPhoneNumber(newUser.getPhoneNumber());
                user.setAddress(newUser.getAddress());
                return repository.save(user);
            })
            .orElseGet(() -> {
                newUser.setId(id);
                return repository.save(newUser);
            });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable String id) {
        repository.deleteById(id);
    }
    
}
