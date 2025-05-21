package com.gestionterrains.gestionterrains.controllers.admin;

import com.gestionterrains.gestionterrains.repositories.ReservationRepository; // Correction ici
import com.gestionterrains.gestionterrains.services.TerrainService;
import com.gestionterrains.gestionterrains.services.UtilisateurService;
import com.gestionterrains.gestionterrains.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final VilleService villeService;
    private final TerrainService terrainService;
    private final UtilisateurService utilisateurService;
    private final ReservationRepository reservationRepository;

    @Autowired
    public AdminDashboardController(VilleService villeService, TerrainService terrainService,
                                    UtilisateurService utilisateurService,
                                    ReservationRepository reservationRepository) {
        this.villeService = villeService;
        this.terrainService = terrainService;
        this.utilisateurService = utilisateurService;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public String dashboard(Model model) {
        // Compter les différentes entités pour le tableau de bord
        long countVilles = villeService.findAll().size();
        long countTerrains = terrainService.findAll().size();
        long countUtilisateurs = utilisateurService.findAll().size();
        long countReservations = reservationRepository.count();

        model.addAttribute("countVilles", countVilles);
        model.addAttribute("countTerrains", countTerrains);
        model.addAttribute("countUtilisateurs", countUtilisateurs);
        model.addAttribute("countReservations", countReservations);

        // Récupérer les dernières réservations
        model.addAttribute("dernieresReservations", reservationRepository.findAll().stream().limit(5).toList());

        return "admin/dashboard";
    }
}