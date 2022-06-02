package com.lapetitesyrienne.api.repository;

import java.util.List;

import com.lapetitesyrienne.api.models.User;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
    Boolean existsByEmail(String email);
    List<User> findByOrderByCreatedAtDesc();
    List<User> findByEmailNotNull(Sort sort);
}