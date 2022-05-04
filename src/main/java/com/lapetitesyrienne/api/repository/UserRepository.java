package com.lapetitesyrienne.api.repository;

import java.util.Optional;

import com.lapetitesyrienne.api.models.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}