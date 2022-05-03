package com.lapetitesyrienne.api.repository;

import com.lapetitesyrienne.api.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
