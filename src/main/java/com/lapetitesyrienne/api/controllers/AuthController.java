package com.lapetitesyrienne.api.controllers;

import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.lapetitesyrienne.api.models.ERole;
import com.lapetitesyrienne.api.models.User;
import com.lapetitesyrienne.api.models.request.LoginRequest;
import com.lapetitesyrienne.api.models.response.JwtResponse;
import com.lapetitesyrienne.api.models.response.MessageResponse;
import com.lapetitesyrienne.api.repository.UserRepository;
import com.lapetitesyrienne.api.security.jwt.JwtUtils;
import com.lapetitesyrienne.api.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    /**
     * Partie Login
     * @param loginRequest
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // generate jwt token for user
        String jwt = jwtUtils.generateJwtToken(authentication);

        // get user details
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println(userDetails);
        // get user role
        String role = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList()).get(0);

        // prepare response object with user details and jwt token
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                role));
    }


    /**
     * Partie Signup
     * @param User for type user
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User signUpRequest, HttpServletRequest request) {
        // verification si le mail existe deja
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(HttpStatus.BAD_REQUEST, "Email address is already used", request.getRequestURI()));
        }
        // set le role par defaut
        signUpRequest.setRole(ERole.ROLE_CLIENT.toString());
        // encode le mot de passe
        signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
        // set creation, modif date
        signUpRequest.setCreatedAt(new Date());
        signUpRequest.setUpdatedAt(new Date());
        // Create new user's account
        userRepository.save(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED,
                "User created successfully", request.getRequestURI()));
    }

    @GetMapping("/testJWT")
    public ResponseEntity<?> testJWT() {
        return ResponseEntity.ok(new MessageResponse("JWT is working!"));
    }
}
