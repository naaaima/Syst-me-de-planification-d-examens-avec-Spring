package com.ensah.core.services.impl;

import com.ensah.core.dao.DepartmentRepository;
import com.ensah.core.dao.EnseignantRepository;
import com.ensah.core.entities.Departement;
import com.ensah.core.entities.Enseignant;
import com.ensah.core.entities.Filiere;
import com.ensah.core.services.IDepartementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartementServiceImpl implements IDepartementService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Departement registerDepartement(Departement departement) {
        return departmentRepository.save(departement);
    }

    @Override
    public List<Departement> getDepartements() {
        return departmentRepository.findAll();
    }

    @Override
    public Departement getDepartementById(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Departement not found"));
    }

    @Override
    public Departement modifierDepartement(Long id, Departement departement) {
        Departement foundedDepartement = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Departement not found"));
        foundedDepartement.setNom(departement.getNom());
        return departmentRepository.save(departement);
    }

    @Override
    public Departement deleteDepartementById(Long id) {
        Departement foundedDepartement = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Departement not found"));
        departmentRepository.deleteById(id);
        return foundedDepartement;
    }

    @Override
    @Transactional
    public List<Enseignant> addEnseignants(Long id, List<Enseignant> enseignants) {
        Departement foundedDepartement = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departement not found"));

        // Add new enseignants and set the departement
        for (Enseignant enseignant : enseignants) {
            enseignant.setDepartement(foundedDepartement);
            foundedDepartement.addEnseignant(enseignant);
        }
        departmentRepository.save(foundedDepartement);

        return foundedDepartement.getEnseignants();
    }

    @Override
    @Transactional
    public void supprimerEnseignant(Long departementId, Long enseignantId) {
        // Retrieve the departement from the database
        Departement departement = departmentRepository.findById(departementId)
                .orElseThrow(() -> new RuntimeException("Departement not found"));

        // Find the enseignant with the given ID in the departement's list of enseignants
        Enseignant enseignantToRemove = departement.getEnseignants().stream()
                .filter(enseignant -> enseignant.getId().equals(enseignantId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Enseignant not found in departement"));

        // Remove the enseignant from the departement's list of enseignants
        departement.getEnseignants().remove(enseignantToRemove);

        // If you have cascade operations configured, you might not need the following lines
        enseignantToRemove.setDepartement(null);

        // Update the departement in the database
        departmentRepository.save(departement);
    }

    @Override
    @Transactional
    public List<Filiere> addFilieres(Long id, List<Filiere> filieres) {
        Departement foundedDepartement = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departement not found"));

        // Add new filieres and set the departement
        for (Filiere filiere : filieres) {
            filiere.setDepartement(foundedDepartement);
            foundedDepartement.addFiliere(filiere);
        }
        departmentRepository.save(foundedDepartement);

        return foundedDepartement.getFilieres();
    }

    @Override
    public void supprimerFiliere(Long departementId, Long filiereId) {
        // Retrieve the departement from the database
        Departement departement = departmentRepository.findById(departementId)
                .orElseThrow(() -> new RuntimeException("Departement not found"));

        // Find the filiere with the given ID in the departement's list of filieres
        Filiere filiereToRemove = departement.getFilieres().stream()
                .filter(filiere -> filiere.getId().equals(filiereId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Filiere not found in departement"));

        // Remove the filiere from the departement's list of filieres
        departement.getFilieres().remove(filiereToRemove);

        // If you have cascade operations configured, you might not need the following lines
        filiereToRemove.setDepartement(null);

        // Update the departement in the database
        departmentRepository.save(departement);
    }
}
