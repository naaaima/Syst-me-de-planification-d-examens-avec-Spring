package com.ensah.core.services.impl;


import com.ensah.core.dao.FiliereRepository;
import com.ensah.core.entities.Filiere;
import com.ensah.core.entities.Enseignant;
import com.ensah.core.services.IFiliereService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FiliereServiceImpl implements IFiliereService {
    private final FiliereRepository filiereRepository;

    @Override
    public Filiere registerFiliere(Filiere departement) {
        return filiereRepository.save(departement);
    }

    @Override
    public List<Filiere> getFilieres() {
        return filiereRepository.findAll();
    }

    @Override
    public Filiere getFiliereById(Long id) {
        return filiereRepository.findById(id).orElseThrow(() -> new RuntimeException("Filiere not found"));
    }

    @Override
    public Filiere modifierFiliere(Long id, Filiere departement) {
        Filiere foundedFiliere = filiereRepository.findById(id).orElseThrow(() -> new RuntimeException("Filiere not found"));
        foundedFiliere.setNom(departement.getNom());
        return filiereRepository.save(departement);
    }

    @Override
    public Filiere deleteFiliereById(Long id) {
        Filiere foundedFiliere = filiereRepository.findById(id).orElseThrow(() -> new RuntimeException("Filiere not found"));
        filiereRepository.deleteById(id);
        return foundedFiliere;
    }

    @Override
    @Transactional
    public List<Enseignant> addEnseignants(Long id, List<Enseignant> enseignants) {
        Filiere foundedFiliere = filiereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filiere not found"));

        // Clear existing enseignants if needed
        foundedFiliere.getEnseignants().clear();

        // Add new enseignants and set the departement
        for (Enseignant enseignant : enseignants) {
            enseignant.setFiliere(foundedFiliere);
            foundedFiliere.addEnseignant(enseignant);
        }
        filiereRepository.save(foundedFiliere);

        return foundedFiliere.getEnseignants();
    }

    @Override
    @Transactional
    public void supprimerEnseignant(Long departementId, Long enseignantId) {
        // Retrieve the departement from the database
        Filiere departement = filiereRepository.findById(departementId)
                .orElseThrow(() -> new RuntimeException("Filiere not found"));

        // Find the enseignant with the given ID in the departement's list of enseignants
        Enseignant enseignantToRemove = departement.getEnseignants().stream()
                .filter(enseignant -> enseignant.getId().equals(enseignantId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Enseignant not found in departement"));

        // Remove the enseignant from the departement's list of enseignants
        departement.getEnseignants().remove(enseignantToRemove);

        // If you have cascade operations configured, you might not need the following lines
        enseignantToRemove.setFiliere(null);

        // Update the departement in the database
        filiereRepository.save(departement);
    }
}
