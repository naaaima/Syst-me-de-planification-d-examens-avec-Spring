package com.ensah.core.dao;

import com.ensah.core.entities.Administrateur;
import com.ensah.core.entities.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> {

    void deleteByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Administrateur> findByUsername(String username);

    boolean existsByEmail(String email);
    Optional<Administrateur> findByEmail(String email);
}
