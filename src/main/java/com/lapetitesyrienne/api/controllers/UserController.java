package com.lapetitesyrienne.api.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.lapetitesyrienne.api.exceptions.UserNotFoundException;
import com.lapetitesyrienne.api.models.User;
import com.lapetitesyrienne.api.models.UserDTO;
import com.lapetitesyrienne.api.models.response.ResponseMessage;
import com.lapetitesyrienne.api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class UserController {
    
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/users")
    List<UserDTO> all() {
        return userRepository.findByOrderByCreatedAtDesc().stream().map(u -> new UserDTO(u)).collect(Collectors.toList());
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @GetMapping("/users/{id}")
    ResponseEntity<?> getUserById(@PathVariable String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable String id) {
        // check user by id exists
        User oldUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        // check email is unique
        if(userRepository.findByEmail(newUser.getEmail()) != null )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Email already exists"));
        // get user info from old user
        newUser.setId(oldUser.getId());
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        newUser.setCreatedAt(oldUser.getCreatedAt());
        newUser.setUpdatedAt(new Date());
        userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
        return ResponseEntity.status(204).body(new ResponseMessage("resource deleted successfully"));
    }
    
}
