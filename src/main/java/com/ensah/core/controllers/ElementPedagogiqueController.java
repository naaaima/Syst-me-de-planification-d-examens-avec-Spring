package com.ensah.core.controllers;

import com.ensah.core.dao.NiveauRepository;
import com.ensah.core.dao.TypeElementPedagogiqueRepository;
import com.ensah.core.entities.*;
import com.ensah.core.services.IElementPedagogiqueService;
import com.ensah.core.services.IEnseignantService;
import com.ensah.core.services.IFiliereService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/elements_pedagogiques")
@RequiredArgsConstructor
public class ElementPedagogiqueController {

    private final IElementPedagogiqueService elementPedagogiqueService;
    private final IEnseignantService enseignantService;
    private final IFiliereService filiereService;
    private final NiveauRepository niveauRepository;
    private final TypeElementPedagogiqueRepository typeElementPedagogiqueRepository;

    @GetMapping
    public String index(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("username", user.getUsername());
        List<ElementPedagogique> elementPedagogiques = this.elementPedagogiqueService.getElementPedagogiques();
        model.addAttribute("elementPedagogiques" , elementPedagogiques);
        return "elements_pedagogiques/index";
    }

    @GetMapping("/ajouter")
    public String ajouterGet(@AuthenticationPrincipal UserDetails user, Model model) {
        List<TypeElementPedagogique> typeElements = typeElementPedagogiqueRepository.findAll();
        List<Niveau> niveaux = niveauRepository.findAll();
        List<Filiere> filieres = filiereService.getFilieres();
        List<Enseignant> enseignants = enseignantService.getEnseignants();
        model.addAttribute("username", user.getUsername());
        model.addAttribute("elementPedagogique", new ElementPedagogique());
        model.addAttribute("types_elements", typeElements);
        model.addAttribute("enseignants", enseignants);
        model.addAttribute("niveaux", niveaux);
        model.addAttribute("filieres", filieres);
        return "elements_pedagogiques/ajouter";
    }

    @PostMapping("/ajouter")
    public String ajouterPost(@Valid @ModelAttribute("elementPedagogique") ElementPedagogique elementPedagogique, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "/elements_pedagogiques/ajouter";
        }

        try{
            elementPedagogiqueService.registerElementPedagogique(elementPedagogique);
            model.addAttribute("success", "elementPedagogique enrigistré avec succée");
            return "redirect:/elements_pedagogiques";
        }catch(Exception ex){
            model.addAttribute("error", "Error : "+ex.getMessage());
            return "/elements_pedagogiques/ajouter";
        }

    }

    @GetMapping("/{id}")
    public String afficherElementPedagogique(@AuthenticationPrincipal UserDetails user, @PathVariable("id") Long id, Model model){
        model.addAttribute("username", user.getUsername());
        ElementPedagogique elementPedagogique = this.elementPedagogiqueService.getElementPedagogiqueById(id);
        model.addAttribute("elementPedagogique" , elementPedagogique);
        return "elements_pedagogiques/afficher";
    }

    @GetMapping("/edit/{id}")
    public String editerElementPedagogique(@AuthenticationPrincipal UserDetails user, @PathVariable("id") Long id, Model model){
        ElementPedagogique elementPedagogique = this.elementPedagogiqueService.getElementPedagogiqueById(id);
        List<TypeElementPedagogique> typeElements = typeElementPedagogiqueRepository.findAll();
        List<Niveau> niveaux = niveauRepository.findAll();
        List<Filiere> filieres = filiereService.getFilieres();
        List<Enseignant> enseignants = enseignantService.getEnseignants();
        model.addAttribute("username", user.getUsername());
        model.addAttribute("elementPedagogique", elementPedagogique);
        model.addAttribute("types_elements", typeElements);
        model.addAttribute("enseignants", enseignants);
        model.addAttribute("niveaux", niveaux);
        model.addAttribute("filieres", filieres);
        return "elements_pedagogiques/editer";
    }

    @PostMapping("/modifier")
    public String modifierElementPedagogique(@Valid @ModelAttribute("elementPedagogique") ElementPedagogique elementPedagogique, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("elementPedagogique", elementPedagogique);
            return "elements_pedagogiques/editer";
        }

        try{
            ElementPedagogique updatedElementPedagogique = this.elementPedagogiqueService.modifierElementPedagogique(elementPedagogique.getId(), elementPedagogique);
            model.addAttribute("elementPedagogique", updatedElementPedagogique);
            model.addAttribute("success", "ElementPedagogique modifié avec succée");
            return "redirect:/elements_pedagogiques";
        }catch(Exception ex){
            model.addAttribute("error", "Error : "+ex.getMessage());
            return "/elements_pedagogiques/editer";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteElementPedagogique(@PathVariable("id") Long id, Model model){

        try{
            elementPedagogiqueService.deleteElementPedagogiqueById(id);
            model.addAttribute("success", "ElementPedagogique supprimé avec succée");
            return "redirect:/elements_pedagogiques";
        }catch(Exception ex){
            model.addAttribute("error", "Error : "+ex.getMessage());
            return "/elements_pedagogiques/editer";
        }
    }
}
