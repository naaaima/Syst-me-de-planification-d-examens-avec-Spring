package com.ensah.core.services;

import com.ensah.core.entities.Administrateur;
import com.ensah.core.entities.Personnel;
import com.ensah.core.entities.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface IAdministrateurService {
    public Administrateur registerAdministrateur(Administrateur administrateur);
    public List<Administrateur> getAdministrateurs();

    public Administrateur getAdministrateurByUsername(String username);

    Administrateur getAdministrateurById(Long id);

    Administrateur modifierAdministrateur(Long id, Administrateur administrateur);

    Administrateur deleteAdministrateurById(Long id);
}
