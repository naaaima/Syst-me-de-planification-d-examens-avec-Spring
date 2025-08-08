package com.ensah.core.services;

import com.ensah.core.entities.Enseignant;
import com.ensah.core.entities.Enseignant;

import java.util.List;

public interface IEnseignantService {
    public Enseignant addEnseignant(Enseignant enseignant);
    public Enseignant registerEnseignant(Enseignant administrateur);
    public List<Enseignant> getEnseignants();

    public void deleteEnseignant(String email);

    public Enseignant getEnseignantByUsername(String username);
    public Enseignant getEnseignant(String email);

    Enseignant getEnseignantById(Long id);

    Enseignant modifierEnseignant(Long id, Enseignant enseignant);

    Enseignant deleteEnseignantById(Long id);

    Enseignant findById(Long id);
}
