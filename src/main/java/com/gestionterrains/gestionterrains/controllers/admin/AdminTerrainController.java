package com.gestionterrains.gestionterrains.controllers.admin;

import com.gestionterrains.gestionterrains.enums.TypeSurface;
import com.gestionterrains.gestionterrains.models.Terrain;
import com.gestionterrains.gestionterrains.services.TerrainService;
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
@RequestMapping("/admin/terrains")
public class AdminTerrainController {

    private final TerrainService terrainService;
    private final VilleService villeService;

    @Autowired
    public AdminTerrainController(TerrainService terrainService, VilleService villeService) {
        this.terrainService = terrainService;
        this.villeService = villeService;
    }

    @GetMapping
    public String listTerrains(Model model) {
        model.addAttribute("terrains", terrainService.findAll());
        model.addAttribute("villes", villeService.findAll());
        model.addAttribute("activeTab", "terrains");
        model.addAttribute("pageTitle", "Gestion des Terrains");
        return "admin/terrains/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("terrain", new Terrain());
        model.addAttribute("villes", villeService.findAll());
        model.addAttribute("typesSurface", TypeSurface.values());
        model.addAttribute("activeTab", "terrains");
        model.addAttribute("pageTitle", "Nouveau Terrain");
        return "admin/terrains/form";
    }

    @PostMapping
    public String createTerrain(@Valid Terrain terrain, BindingResult result, 
                              RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors() || terrain.getVille() == null) {
            model.addAttribute("villes", villeService.findAll());
            model.addAttribute("typesSurface", TypeSurface.values());
            model.addAttribute("activeTab", "terrains");
            model.addAttribute("pageTitle", "Nouveau Terrain");
            if (terrain.getVille() == null) {
                model.addAttribute("villeError", "Veuillez sélectionner une ville");
            }
            return "admin/terrains/form";
        }

        terrainService.save(terrain);
        redirectAttributes.addFlashAttribute("success", "Terrain ajouté avec succès");
        return "redirect:/admin/terrains";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Terrain> terrain = terrainService.findById(id);
        if (terrain.isPresent()) {
            model.addAttribute("terrain", terrain.get());
            model.addAttribute("villes", villeService.findAll());
            model.addAttribute("typesSurface", TypeSurface.values());
            model.addAttribute("activeTab", "terrains");
            model.addAttribute("pageTitle", "Modifier Terrain");
            return "admin/terrains/form";
        } else {
            return "redirect:/admin/terrains";
        }
    }

    @PostMapping("/{id}")
    public String updateTerrain(@PathVariable Long id, @Valid Terrain terrain, BindingResult result, 
                              RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors() || terrain.getVille() == null) {
            model.addAttribute("villes", villeService.findAll());
            model.addAttribute("typesSurface", TypeSurface.values());
            model.addAttribute("activeTab", "terrains");
            model.addAttribute("pageTitle", "Modifier Terrain");
            if (terrain.getVille() == null) {
                model.addAttribute("villeError", "Veuillez sélectionner une ville");
            }
            return "admin/terrains/form";
        }

        terrain.setId(id);
        terrainService.save(terrain);
        redirectAttributes.addFlashAttribute("success", "Terrain mis à jour avec succès");
        return "redirect:/admin/terrains";
    }

    @PostMapping("/delete")
    public String deleteTerrain(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            terrainService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Terrain supprimé avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Impossible de supprimer ce terrain car il est référencé par des réservations");
        }
        return "redirect:/admin/terrains";
    }
}