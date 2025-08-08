package com.ensah.core.services.impl;

import com.ensah.core.dao.ElementPedagogiqueRepository;
import com.ensah.core.entities.ElementPedagogique;
import com.ensah.core.services.IElementPedagogiqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ElementPedagogiqueServiceImpl implements IElementPedagogiqueService {

    private final ElementPedagogiqueRepository elementPedagogiqueRepository;

    @Override
    public ElementPedagogique registerElementPedagogique(ElementPedagogique elementPedagogique) {
        return elementPedagogiqueRepository.save(elementPedagogique);
    }

    @Override
    public List<ElementPedagogique> getElementPedagogiques() {
        return elementPedagogiqueRepository.findAll();
    }

    @Override
    public ElementPedagogique getElementPedagogiqueById(Long id) {
        return elementPedagogiqueRepository.findById(id).orElseThrow(() -> new RuntimeException("ElementPedagogique not found"));
    }

    @Override
    @Transactional
    public ElementPedagogique modifierElementPedagogique(Long id, ElementPedagogique elementPedagogique) {
        ElementPedagogique foundedElementPedagogique = elementPedagogiqueRepository.findById(id).orElseThrow(() -> new RuntimeException("ElementPedagogique not found"));
        System.out.println("-------------------- ");
        System.out.println("getted element pedagogique : "+foundedElementPedagogique);
        foundedElementPedagogique.setTypeElement(elementPedagogique.getTypeElement());
        foundedElementPedagogique.setTitre(elementPedagogique.getTitre());
        foundedElementPedagogique.setCoordonateur(elementPedagogique.getCoordonateur());
        foundedElementPedagogique.setFiliere(elementPedagogique.getFiliere());
        foundedElementPedagogique.setNiveau(elementPedagogique.getNiveau());
        return elementPedagogiqueRepository.save(foundedElementPedagogique);
    }

    @Override
    @Transactional
    public ElementPedagogique deleteElementPedagogiqueById(Long id) {
        ElementPedagogique foundedElementPedagogique = elementPedagogiqueRepository.findById(id).orElseThrow(() -> new RuntimeException("ElementPedagogique not found"));
        elementPedagogiqueRepository.deleteById(id);
        return foundedElementPedagogique;
    }
    
}
