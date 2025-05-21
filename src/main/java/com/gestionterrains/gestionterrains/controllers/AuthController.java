package com.gestionterrains.gestionterrains.controllers;

import com.gestionterrains.gestionterrains.enums.Role;
import com.gestionterrains.gestionterrains.models.Utilisateur;
import com.gestionterrains.gestionterrains.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Contrôleur gérant toutes les fonctionnalités d'authentification
 * (connexion, inscription, déconnexion)
 */
@Controller
public class AuthController {

    @Autowired
    private UtilisateurService utilisateurService;

    /**
     * Affiche la page de connexion
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Affiche le formulaire d'inscription
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "register";
    }

    /**
     * Traite la soumission du formulaire d'inscription
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute Utilisateur utilisateur, BindingResult result, RedirectAttributes redirectAttributes) {
        // Vérifier si l'email existe déjà
        if (utilisateurService.emailExists(utilisateur.getEmail())) {
            result.rejectValue("email", "error.utilisateur", "Cet email est déjà utilisé");
            redirectAttributes.addFlashAttribute("error", "Cet email est déjà utilisé");
            return "redirect:/register";
        }

        // Définir le rôle par défaut à UTILISATEUR
        utilisateur.setRole(Role.UTILISATEUR);
        
        // Enregistrer l'utilisateur
        utilisateurService.save(utilisateur);
        
        // Rediriger vers la page de connexion avec un message de succès
        redirectAttributes.addFlashAttribute("registerSuccess", "Votre compte a été créé avec succès. Veuillez vous connecter.");
        return "redirect:/login";
    }
}