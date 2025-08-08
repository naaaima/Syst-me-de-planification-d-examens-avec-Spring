package com.ensah.core.dao;

import com.ensah.core.entities.Administrateur;
import com.ensah.core.entities.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnseignantRepository  extends JpaRepository<Enseignant, Long> {
    void deleteByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Enseignant> findByUsername(String username);

    boolean existsByEmail(String email);
    Optional<Enseignant> findByEmail(String email);
}
