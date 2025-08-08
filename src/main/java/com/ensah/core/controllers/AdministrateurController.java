package com.ensah.core.controllers;

import com.ensah.core.entities.Administrateur;
import com.ensah.core.services.IAdministrateurService;
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
@RequestMapping("/administrateurs")
@RequiredArgsConstructor
public class AdministrateurController {

    private final IAdministrateurService administrateurService;

    @GetMapping
    public String index(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("username", user.getUsername());
        List<Administrateur> administrateurs = this.administrateurService.getAdministrateurs().stream().filter((administrateur) -> !Objects.equals(administrateur.getUsername(), user.getUsername())).toList();
        model.addAttribute("administrateurs" , administrateurs);
        return "administrateurs/index";
    }

    @GetMapping("/ajouter")
    public String ajouterGet(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("administrateur", new Administrateur());
        return "administrateurs/ajouter";
    }

    @PostMapping("/ajouter")
    public String ajouterPost(@Valid @ModelAttribute("administrateur") Administrateur administrateur, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "/administrateurs/ajouter";
        }

        try{
            administrateurService.registerAdministrateur(administrateur);
            model.addAttribute("success", "administrateur enrigistré avec succée");
            return "redirect:/administrateurs";
        }catch(Exception ex){
            model.addAttribute("error", "Error : "+ex.getMessage());
            return "/administrateurs/ajouter";
        }

    }

    @GetMapping("/{id}")
    public String afficherAdministrateur(@AuthenticationPrincipal UserDetails user, @PathVariable("id") Long id, Model model){
        model.addAttribute("username", user.getUsername());
        Administrateur administrateur = this.administrateurService.getAdministrateurById(id);
        model.addAttribute("administrateur" , administrateur);
        return "administrateurs/afficher";
    }

    @GetMapping("/edit/{id}")
    public String editerAdministrateur(@AuthenticationPrincipal UserDetails user, @PathVariable("id") Long id, Model model){
        model.addAttribute("username", user.getUsername());
        Administrateur administrateur = this.administrateurService.getAdministrateurById(id);
        model.addAttribute("administrateur" , administrateur);
        return "administrateurs/editer";
    }

    @PostMapping("/modifier")
    public String modifierAdministrateur(@Valid @ModelAttribute("administrateur") Administrateur administrateur, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("administrateur", administrateur);
            return "administrateurs/editer";
        }

        try{
            Administrateur updatedAdministrateur = this.administrateurService.modifierAdministrateur(administrateur.getId(), administrateur);
            model.addAttribute("administrateur", updatedAdministrateur);
            model.addAttribute("success", "Administrateur modifié avec succée");
            return "redirect:/administrateurs";
        }catch(Exception ex){
            model.addAttribute("error", "Error : "+ex.getMessage());
            return "/administrateurs/editer";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteAdministrateur(@PathVariable("id") Long id, Model model){

        try{
            administrateurService.deleteAdministrateurById(id);
            model.addAttribute("success", "Administrateur supprimé avec succée");
            return "redirect:/administrateurs";
        }catch(Exception ex){
            model.addAttribute("error", "Error : "+ex.getMessage());
            return "/administrateurs/editer";
        }
    }
}
