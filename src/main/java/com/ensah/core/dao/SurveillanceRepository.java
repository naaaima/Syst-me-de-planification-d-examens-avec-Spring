package com.ensah.core.dao;

import com.ensah.core.entities.Surveillance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveillanceRepository extends JpaRepository<Surveillance, Long> {
}
