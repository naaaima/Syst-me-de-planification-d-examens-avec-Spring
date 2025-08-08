package com.ensah.core.controllers;

import com.ensah.core.entities.Departement;
import com.ensah.core.entities.Enseignant;
import com.ensah.core.entities.Filiere;
import com.ensah.core.services.IDepartementService;
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
import java.util.Objects;

@Controller
@RequestMapping("/departements")
@RequiredArgsConstructor
public class DepartementController {

    private final IDepartementService departementService;
    private final IEnseignantService enseignantService;
    private final IFiliereService filiereService;

    @GetMapping
    public String index(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("username", user.getUsername());
        List<Departement> departements = this.departementService.getDepartements();
        model.addAttribute("departements" , departements);
        return "departements/index";
    }

    @GetMapping("/ajouter")
    public String ajouterGet(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("departement", new Departement());
        return "departements/ajouter";
    }

    @PostMapping("/ajouter")
    public String ajouterPost(@Valid @ModelAttribute("departement") Departement departement, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "/departements/ajouter";
        }

        try{
            departementService.registerDepartement(departement);
            model.addAttribute("success", "departement enrigistré avec succée");
            return "redirect:/departements";
        }catch(Exception ex){
            model.addAttribute("error", "Error : "+ex.getMessage());
            return "/departements/ajouter";
        }

    }

    @GetMapping("/{id}")
    public String afficherDepartement(@AuthenticationPrincipal UserDetails user, @PathVariable("id") Long id, Model model){
        model.addAttribute("username", user.getUsername());
        Departement departement = this.departementService.getDepartementById(id);
        model.addAttribute("departement" , departement);
        return "departements/afficher";
    }

    @GetMapping("/edit/{id}")
    public String editerDepartement(@AuthenticationPrincipal UserDetails user, @PathVariable("id") Long id, Model model){
        model.addAttribute("username", user.getUsername());
        Departement departement = this.departementService.getDepartementById(id);
        model.addAttribute("departement" , departement);
        return "departements/editer";
    }

    @PostMapping("/modifier")
    public String modifierDepartement(@Valid @ModelAttribute("departement") Departement departement, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("departement", departement);
            return "departements/editer";
        }

        try{
            Departement updatedDepartement = this.departementService.modifierDepartement(departement.getId(), departement);
            model.addAttribute("departement", updatedDepartement);
            model.addAttribute("success", "Departement modifié avec succée");
            return "redirect:/departements";
        }catch(Exception ex){
            model.addAttribute("error", "Error : "+ex.getMessage());
            return "/departements/editer";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartement(@PathVariable("id") Long id, Model model){

        try{
            departementService.deleteDepartementById(id);
            model.addAttribute("success", "Departement supprimé avec succée");
            return "redirect:/departements";
        }catch(Exception ex){
            model.addAttribute("error", "Error : "+ex.getMessage());
            return "/departements/editer";
        }
    }

    @GetMapping("/ajouter_enseignant/{id}")
    public String ajouterEnseignantGet(@AuthenticationPrincipal UserDetails user, @PathVariable("id") Long id, Model model){
        model.addAttribute("username", user.getUsername());
        Departement foundedDepartement = this.departementService.getDepartementById(id);
        List<Enseignant> enseignantsList = enseignantService.getEnseignants().stream().filter(enseignant -> !foundedDepartement.getEnseignants().contains(enseignant)).toList();
        if (enseignantsList.isEmpty()) {
            return "redirect:/departements";
        }
        model.addAttribute("enseignants", enseignantsList);
        model.addAttribute("departement", foundedDepartement);
        return "departements/ajouter_enseignant";
    }

    @PostMapping("/ajouter_enseignant")
    public String ajouterEnseignantPost(@ModelAttribute Departement departement, RedirectAttributes redirectAttributes) {
        try {
            Departement existingDepartement = departementService.getDepartementById(departement.getId());
            List<Enseignant> selectedEnseignants = new ArrayList<>();

            for (Enseignant enseignant : departement.getEnseignants()) {
                Enseignant foundEnseignant = enseignantService.getEnseignantById(enseignant.getId());
                if (foundEnseignant != null) {
                    selectedEnseignants.add(foundEnseignant);
                }
            }

            departementService.addEnseignants(existingDepartement.getId(), selectedEnseignants);
            redirectAttributes.addFlashAttribute("success", "Enseignants ajoutés avec succès.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout des enseignants.");
        }

        return "redirect:/departements";
    }

    @GetMapping("/supprimer_enseignant/{departementId}/{enseignantId}")
    public String supprimerEnseignant(@PathVariable("departementId") Long departementId,
                                      @PathVariable("enseignantId") Long enseignantId) {
        departementService.supprimerEnseignant(departementId, enseignantId);
        return "redirect:/departements";
    }

    @GetMapping("/ajouter_filiere/{id}")
    public String ajouterFiliereGet(@AuthenticationPrincipal UserDetails user, @PathVariable("id") Long id, Model model){
        model.addAttribute("username", user.getUsername());
        Departement foundedDepartement = this.departementService.getDepartementById(id);
        List<Filiere> filieresList = filiereService.getFilieres().stream().filter(filiere -> !foundedDepartement.getFilieres().contains(filiere)).toList();
        if (filieresList.isEmpty()) {
            return "redirect:/departements";
        }
        model.addAttribute("filieres", filieresList);
        model.addAttribute("departement", foundedDepartement);
        return "departements/ajouter_filiere";
    }

    @PostMapping("/ajouter_filiere")
    public String ajouterFilierePost(@ModelAttribute Departement departement, RedirectAttributes redirectAttributes) {
        try {
            Departement existingDepartement = departementService.getDepartementById(departement.getId());
            List<Filiere> selectedFilieres = new ArrayList<>();

            for (Filiere filiere : departement.getFilieres()) {
                Filiere foundFiliere = filiereService.getFiliereById(filiere.getId());
                if (foundFiliere != null) {
                    selectedFilieres.add(foundFiliere);
                }
            }

            departementService.addFilieres(existingDepartement.getId(), selectedFilieres);
            redirectAttributes.addFlashAttribute("success", "Filieres ajoutés avec succès.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout des filieres.");
        }

        return "redirect:/departements";
    }

    @GetMapping("/supprimer_filiere/{departementId}/{filiereId}")
    public String supprimerFiliere(@PathVariable("departementId") Long departementId,
                                      @PathVariable("filiereId") Long filiereId) {
        departementService.supprimerFiliere(departementId, filiereId);
        return "redirect:/departements";
    }
}
