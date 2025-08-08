package com.ensah.core.controllers;

import com.ensah.core.entities.Enseignant;
import com.ensah.core.services.IEnseignantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/enseignants")
@RequiredArgsConstructor
public class EnseignantController {

    private final IEnseignantService enseignantService;

    @GetMapping
    public String index(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("username", user.getUsername());
        List<Enseignant> enseignants = this.enseignantService.getEnseignants().stream().filter((enseignant) -> !Objects.equals(enseignant.getUsername(), user.getUsername())).toList();
        model.addAttribute("enseignants" , enseignants);
        return "enseignants/index";
    }

    @GetMapping("/ajouter")
    public String ajouterGet(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("enseignant", new Enseignant());
        return "enseignants/ajouter";
    }

    @PostMapping("/ajouter")
    public String ajouterPost(@Valid @ModelAttribute("enseignant") Enseignant enseignant, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "/enseignants/ajouter";
        }

        try{
            enseignantService.registerEnseignant(enseignant);
            model.addAttribute("success", "enseignant enrigistré avec succée");
            return "redirect:/enseignants";
        }catch(Exception ex){
            model.addAttribute("error", "Error : "+ex.getMessage());
            return "/enseignants/ajouter";
        }

    }

    @GetMapping("/{id}")
    public String afficherEnseignant(@AuthenticationPrincipal UserDetails user, @PathVariable("id") Long id, Model model){
        model.addAttribute("username", user.getUsername());
        Enseignant enseignant = this.enseignantService.getEnseignantById(id);
        model.addAttribute("enseignant" , enseignant);
        return "enseignants/afficher";
    }

    @GetMapping("/edit/{id}")
    public String editerEnseignant(@AuthenticationPrincipal UserDetails user, @PathVariable("id") Long id, Model model){
        model.addAttribute("username", user.getUsername());
        Enseignant enseignant = this.enseignantService.getEnseignantById(id);
        model.addAttribute("enseignant" , enseignant);
        return "enseignants/editer";
    }

    @PostMapping("/modifier")
    public String modifierEnseignant(@Valid @ModelAttribute("enseignant") Enseignant enseignant, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("enseignant", enseignant);
            return "enseignants/editer";
        }

        try{
            Enseignant updatedEnseignant = this.enseignantService.modifierEnseignant(enseignant.getId(), enseignant);
            model.addAttribute("enseignant", updatedEnseignant);
            model.addAttribute("success", "Enseignant modifié avec succée");
            return "redirect:/enseignants";
        }catch(Exception ex){
            model.addAttribute("error", "Error : "+ex.getMessage());
            return "/enseignants/editer";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteEnseignant(@PathVariable("id") Long id, Model model){

        try{
            enseignantService.deleteEnseignantById(id);
            model.addAttribute("success", "Enseignant supprimé avec succée");
            return "redirect:/enseignants";
        }catch(Exception ex){
            model.addAttribute("error", "Error : "+ex.getMessage());
            return "/enseignants/editer";
        }
    }
}
