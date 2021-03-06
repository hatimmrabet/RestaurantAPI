package com.lapetitesyrienne.api.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.lapetitesyrienne.api.exceptions.UserNotFoundException;
import com.lapetitesyrienne.api.models.Commande;
import com.lapetitesyrienne.api.models.User;
import com.lapetitesyrienne.api.models.UserDTO;
import com.lapetitesyrienne.api.models.request.UserPutRequest;
import com.lapetitesyrienne.api.models.response.ResponseMessage;
import com.lapetitesyrienne.api.repository.CommandeRepository;
import com.lapetitesyrienne.api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    CommandeRepository commandeRepository;
    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/users")
    List<UserDTO> all() {
        return userRepository.findByEmailNotNull(Sort.by(Sort.Direction.DESC,"createdAt")).stream().map(u -> new UserDTO(u)).collect(Collectors.toList());
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
    ResponseEntity<?> replaceUser(@RequestBody UserPutRequest userBody, @PathVariable String id) {
        // check user by id exists
        User oldUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        User newUser = new User(userBody);
        if(!oldUser.getEmail().equals(newUser.getEmail())) {
            // check email is unique
            if(userRepository.findByEmail(newUser.getEmail()) != null )
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Email already exists"));
        }
        // get user info from old user
        newUser.setId(oldUser.getId());
        if(userBody.getPassword() != null)
            newUser.setPassword(encoder.encode(userBody.getPassword()));
        else
            newUser.setPassword(oldUser.getPassword());
        newUser.setCreatedAt(oldUser.getCreatedAt());
        newUser.setUpdatedAt(new Date());
        userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable String id) {
        // verifier si l'utilisateur existe
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        // verifier si l'utilisateur n'a pas de commande
        List<Commande> commandes = commandeRepository.findByClientOrderByDateDesc(user);
        if(commandes.size() > 0)
        {
            user.setFirstName("Deleted");
            user.setLastName("User");
            user.setEmail(null);
            user.setPhoneNumber(null);
            user.setAddress(null);
            user.setPassword(null);
            user.setBirthDate(null);
            user.setUpdatedAt(new Date());
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("User data deleted successfully"));
        } else {
            userRepository.delete(user);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("User deleted successfully"));
        }
    }
    
}
