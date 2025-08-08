package com.ensah.core.services;

import com.ensah.core.entities.Departement;
import com.ensah.core.entities.Enseignant;
import com.ensah.core.entities.Filiere;

import java.util.List;

public interface IDepartementService {
    public Departement registerDepartement(Departement departement);
    public List<Departement> getDepartements();

    Departement getDepartementById(Long id);

    Departement modifierDepartement(Long id, Departement departement);

    Departement deleteDepartementById(Long id);

    List<Enseignant> addEnseignants(Long id, List<Enseignant> enseignants);

    void supprimerEnseignant(Long departementId, Long enseignantId);

    List<Filiere> addFilieres(Long id, List<Filiere> selectedFilieres);

    void supprimerFiliere(Long departementId, Long filiereId);
}
