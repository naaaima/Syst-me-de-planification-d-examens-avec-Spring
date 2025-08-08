package com.ensah.core.services;

import com.ensah.core.entities.ElementPedagogique;
import com.ensah.core.entities.Enseignant;
import com.ensah.core.entities.Filiere;

import java.util.List;

public interface IElementPedagogiqueService {
    public ElementPedagogique registerElementPedagogique(ElementPedagogique elementPedagogique);
    public List<ElementPedagogique> getElementPedagogiques();

    ElementPedagogique getElementPedagogiqueById(Long id);

    ElementPedagogique modifierElementPedagogique(Long id, ElementPedagogique elementPedagogique);

    ElementPedagogique deleteElementPedagogiqueById(Long id);
/*
    List<Enseignant> addEnseignants(Long id, List<Enseignant> enseignants);

    void supprimerEnseignant(Long elementPedagogiqueId, Long enseignantId);

    List<Filiere> addFilieres(Long id, List<Filiere> selectedFilieres);

    void supprimerFiliere(Long elementPedagogiqueId, Long filiereId);

 */
}
