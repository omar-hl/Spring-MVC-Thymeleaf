package com.example.patientsmvc.repositories;

import com.example.patientsmvc.entities.patient;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;


public interface patientRepo extends JpaRepository<patient,Long> {
 Page<patient> findByNomContains(String kw , Pageable pageable);
}
