package com.gestionterrains.gestionterrains.controllers.admin;

import com.gestionterrains.gestionterrains.models.Utilisateur;
import com.gestionterrains.gestionterrains.services.UtilisateurService;
import com.gestionterrains.gestionterrains.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/utilisateurs")
public class AdminUtilisateurController {

    private final UtilisateurService utilisateurService;

    @Autowired
    public AdminUtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }
    
    @GetMapping
    public String listUtilisateurs(Model model) {
        model.addAttribute("utilisateurs", utilisateurService.findAll());
        return "admin/utilisateurs/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("roles", Role.values());
        return "admin/utilisateurs/form";
    }

    @PostMapping
    public String createUtilisateur(@ModelAttribute Utilisateur utilisateur, RedirectAttributes redirectAttributes) {
        utilisateurService.save(utilisateur);
        redirectAttributes.addFlashAttribute("success", "Utilisateur créé avec succès");
        return "redirect:/admin/utilisateurs";
    }

    @PostMapping("/save")
    public String saveUtilisateur(@ModelAttribute Utilisateur utilisateur, RedirectAttributes redirectAttributes) {
        try {
            utilisateurService.save(utilisateur);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur enregistré avec succès");
            return "redirect:/admin/utilisateurs";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'enregistrement: " + e.getMessage());
            return "redirect:/admin/utilisateurs/new";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        utilisateurService.findById(id).ifPresent(utilisateur -> model.addAttribute("utilisateur", utilisateur));
        model.addAttribute("roles", Role.values());
        return "admin/utilisateurs/form";
    }

    @PostMapping("/{id}")
    public String updateUtilisateur(@PathVariable Long id, @ModelAttribute Utilisateur utilisateur, RedirectAttributes redirectAttributes) {
        utilisateur.setId(id);
        utilisateurService.save(utilisateur);
        redirectAttributes.addFlashAttribute("success", "Utilisateur mis à jour avec succès");
        return "redirect:/admin/utilisateurs";
    }

    @GetMapping("/{id}/delete")
    public String deleteUtilisateur(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        utilisateurService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Utilisateur supprimé avec succès");
        return "redirect:/admin/utilisateurs";
    }
}