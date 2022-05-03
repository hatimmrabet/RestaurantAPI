package com.lapetitesyrienne.api.repository;

import com.lapetitesyrienne.api.models.Employee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}