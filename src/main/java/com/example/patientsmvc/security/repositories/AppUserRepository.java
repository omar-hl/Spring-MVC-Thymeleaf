package com.example.patientsmvc.security.repositories;

import com.example.patientsmvc.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository <AppUser, String> {
    public AppUser findByUsername(String username);
}
