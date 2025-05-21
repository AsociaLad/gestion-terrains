package com.gestionterrains.gestionterrains.controllers.admin;

import com.gestionterrains.gestionterrains.models.Ville;
import com.gestionterrains.gestionterrains.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin/villes")
public class AdminVilleController {

    private final VilleService villeService;

    @Autowired
    public AdminVilleController(VilleService villeService) {
        this.villeService = villeService;
    }

    @GetMapping
    public String listVilles(Model model) {
        model.addAttribute("villes", villeService.findAll());
        model.addAttribute("activeTab", "villes");
        model.addAttribute("pageTitle", "Gestion des Villes");
        return "admin/villes/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("ville", new Ville());
        model.addAttribute("activeTab", "villes");
        model.addAttribute("pageTitle", "Nouvelle Ville");
        return "admin/villes/form";
    }

    @PostMapping
    public String createVille(@Valid Ville ville, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("activeTab", "villes");
            model.addAttribute("pageTitle", "Nouvelle Ville");
            return "admin/villes/form";
        }
        
        villeService.save(ville);
        redirectAttributes.addFlashAttribute("success", "Ville ajoutée avec succès");
        return "redirect:/admin/villes";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Ville> ville = villeService.findById(id);
        if (ville.isPresent()) {
            model.addAttribute("ville", ville.get());
            model.addAttribute("activeTab", "villes");
            model.addAttribute("pageTitle", "Modifier Ville");
            return "admin/villes/form";
        } else {
            return "redirect:/admin/villes";
        }
    }

    @PostMapping("/{id}")
    public String updateVille(@PathVariable Long id, @Valid Ville ville, BindingResult result, 
                             RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("activeTab", "villes");
            model.addAttribute("pageTitle", "Modifier Ville");
            return "admin/villes/form";
        }
        
        ville.setId(id);
        villeService.save(ville);
        redirectAttributes.addFlashAttribute("success", "Ville mise à jour avec succès");
        return "redirect:/admin/villes";
    }

    @GetMapping("/{id}/delete")
    public String deleteVille(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            villeService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Ville supprimée avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Impossible de supprimer cette ville car elle est référencée par d'autres entités");
        }
        return "redirect:/admin/villes";
    }
}