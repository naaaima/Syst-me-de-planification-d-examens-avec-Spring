package com.ensah.core.dao;

import com.ensah.core.entities.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Departement, Long> {
}
