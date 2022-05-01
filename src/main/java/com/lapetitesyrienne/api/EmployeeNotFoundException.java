package com.lapetitesyrienne.api;

class EmployeeNotFoundException extends RuntimeException {

    EmployeeNotFoundException(Long id) {
      super("Could not find employee " + id);
    }
  }