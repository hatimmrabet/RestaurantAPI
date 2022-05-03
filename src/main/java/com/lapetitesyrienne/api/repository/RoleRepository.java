package com.lapetitesyrienne.api.repository;

import java.util.Optional;

import com.lapetitesyrienne.api.models.ERole;
import com.lapetitesyrienne.api.models.Role;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}