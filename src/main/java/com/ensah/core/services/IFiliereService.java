package com.ensah.core.services;

import com.ensah.core.entities.Filiere;
import com.ensah.core.entities.Enseignant;

import java.util.List;

public interface IFiliereService {
    public Filiere registerFiliere(Filiere departement);
    public List<Filiere> getFilieres();

    Filiere getFiliereById(Long id);

    Filiere modifierFiliere(Long id, Filiere departement);

    Filiere deleteFiliereById(Long id);

    List<Enseignant> addEnseignants(Long id, List<Enseignant> enseignants);

    void supprimerEnseignant(Long departementId, Long enseignantId);
}
