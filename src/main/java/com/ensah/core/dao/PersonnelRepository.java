package com.ensah.core.dao;

import com.ensah.core.entities.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonnelRepository  extends JpaRepository<Personnel, Long> {
    Optional<Personnel> findByUsername(String username);
}
