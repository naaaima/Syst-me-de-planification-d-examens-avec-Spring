package com.ensah.core.controllers;

import com.ensah.core.entities.Departement;
import com.ensah.core.entities.Filiere;
import com.ensah.core.entities.Enseignant;
import com.ensah.core.services.IDepartementService;
import com.ensah.core.services.IFiliereService;
import com.ensah.core.services.IEnseignantService;
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
@RequestMapping("/filieres")
@RequiredArgsConstructor
public class FiliereController {

    private final IFiliereService filiereService;
    private final IEnseignantService enseignantService;
    private final IDepartementService departementService;

    @GetMapping
    public String index(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("username", user.getUsername());
        List<Filiere> filieres = this.filiereService.getFilieres();
        model.addAttribute("filieres" , filieres);
        return "filieres/index";
    }

    @GetMapping("/ajouter")
    public String ajouterGet(@AuthenticationPrincipal UserDetails user, Model model) {
        List<Departement> departements = departementService.getDepartements();
        model.addAttribute("username", user.getUsername());
        model.addAttribute("departements", departements);
        model.addAttribute("filiere", new Filiere());
        return "filieres/ajouter";
    }

    @PostMapping("/ajouter")
    public String ajouterPost(@Valid @ModelAttribute("filiere") Filiere filiere, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "/filieres/ajouter";
        }

        try{
            filiereService.registerFiliere(filiere);
            model.addAttribute("success", "filiere enrigistré avec succée");
            return "redirect:/filieres";
        }catch(Exception ex){
            model.addAttribute("error", "Error : "+ex.getMessage());
            return "/filieres/ajouter";
        }

    }

    @GetMapping("/{id}")
    public String afficherFiliere(@AuthenticationPrincipal UserDetails user, @PathVariable("id") Long id, Model model){
        model.addAttribute("username", user.getUsername());
        Filiere filiere = this.filiereService.getFiliereById(id);
        model.addAttribute("filiere" , filiere);
        return "filieres/afficher";
    }

    @GetMapping("/edit/{id}")
    public String editerFiliere(@AuthenticationPrincipal UserDetails user, @PathVariable("id") Long id, Model model){
        model.addAttribute("username", user.getUsername());
        List<Departement> departements = departementService.getDepartements();
        Filiere filiere = this.filiereService.getFiliereById(id);
        model.addAttribute("filiere" , filiere);
        model.addAttribute("departements", departements);
        return "filieres/editer";
    }

    @PostMapping("/modifier")
    public String modifierFiliere(@Valid @ModelAttribute("filiere") Filiere filiere, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("filiere", filiere);
            return "filieres/editer";
        }

        try{
            Filiere updatedFiliere = this.filiereService.modifierFiliere(filiere.getId(), filiere);
            model.addAttribute("filiere", updatedFiliere);
            model.addAttribute("success", "Filiere modifié avec succée");
            return "redirect:/filieres";
        }catch(Exception ex){
            model.addAttribute("error", "Error : "+ex.getMessage());
            return "/filieres/editer";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteFiliere(@PathVariable("id") Long id, Model model){

        try{
            filiereService.deleteFiliereById(id);
            model.addAttribute("success", "Filiere supprimé avec succée");
            return "redirect:/filieres";
        }catch(Exception ex){
            model.addAttribute("error", "Error : "+ex.getMessage());
            return "/filieres/editer";
        }
    }

    @GetMapping("/ajouter_enseignant/{id}")
    public String ajouterEnseignantGet(@AuthenticationPrincipal UserDetails user, @PathVariable("id") Long id, Model model){
        model.addAttribute("username", user.getUsername());
        Filiere foundedFiliere = this.filiereService.getFiliereById(id);
        List<Enseignant> enseignantsList = enseignantService.getEnseignants().stream().filter(enseignant -> !foundedFiliere.getEnseignants().contains(enseignant)).toList();
        if (enseignantsList.isEmpty()) {
            return "redirect:/filieres";
        }
        model.addAttribute("enseignants", enseignantsList);
        model.addAttribute("filiere", foundedFiliere);
        return "filieres/ajouter_enseignant";
    }

    @PostMapping("/ajouter_enseignant")
    public String ajouterEnseignantPost(@ModelAttribute Filiere filiere, RedirectAttributes redirectAttributes) {
        try {
            Filiere existingFiliere = filiereService.getFiliereById(filiere.getId());
            List<Enseignant> selectedEnseignants = new ArrayList<>();

            for (Enseignant enseignant : filiere.getEnseignants()) {
                Enseignant foundEnseignant = enseignantService.getEnseignantById(enseignant.getId());
                if (foundEnseignant != null) {
                    selectedEnseignants.add(foundEnseignant);
                }
            }

            filiereService.addEnseignants(existingFiliere.getId(), selectedEnseignants);
            redirectAttributes.addFlashAttribute("success", "Enseignants ajoutés avec succès.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout des enseignants.");
        }

        return "redirect:/filieres";
    }

    @GetMapping("/supprimer_enseignant/{filiereId}/{enseignantId}")
    public String supprimerEnseignant(@PathVariable("filiereId") Long filiereId,
                                      @PathVariable("enseignantId") Long enseignantId) {
        filiereService.supprimerEnseignant(filiereId, enseignantId);
        return "redirect:/filieres";
    }
}
