package com.example.patientsmvc.security.repositories;

import com.example.patientsmvc.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository <AppRole, Long> {
    public AppRole findByRole(String role);
}
