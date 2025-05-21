package com.gestionterrains.gestionterrains.controllers.admin;

import com.gestionterrains.gestionterrains.models.Reservation;
import com.gestionterrains.gestionterrains.enums.StatutReservation;
import com.gestionterrains.gestionterrains.repositories.ReservationRepository;
import com.gestionterrains.gestionterrains.services.TerrainService;
import com.gestionterrains.gestionterrains.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/reservations")
public class AdminReservationController {

    private final ReservationRepository reservationRepository;
    private final TerrainService terrainService;
    private final UtilisateurService utilisateurService;

    @Autowired
    public AdminReservationController(ReservationRepository reservationRepository,
                                      TerrainService terrainService,
                                      UtilisateurService utilisateurService) {
        this.reservationRepository = reservationRepository;
        this.terrainService = terrainService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public String listReservations(Model model) {
        model.addAttribute("reservations", reservationRepository.findAll());
        return "admin/reservations/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("terrains", terrainService.findAll());
        model.addAttribute("utilisateurs", utilisateurService.findAll());
        model.addAttribute("statuts", StatutReservation.values());
        return "admin/reservations/form";
    }

    @PostMapping
    public String createReservation(@ModelAttribute Reservation reservation, RedirectAttributes redirectAttributes) {
        reservationRepository.save(reservation);
        redirectAttributes.addFlashAttribute("success", "Réservation créée avec succès");
        return "redirect:/admin/reservations";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        reservationRepository.findById(id).ifPresent(reservation -> model.addAttribute("reservation", reservation));
        model.addAttribute("terrains", terrainService.findAll());
        model.addAttribute("utilisateurs", utilisateurService.findAll());
        model.addAttribute("statuts", StatutReservation.values());
        return "admin/reservations/form";
    }

    @PostMapping("/{id}")
    public String updateReservation(@PathVariable Long id, @ModelAttribute Reservation reservation, RedirectAttributes redirectAttributes) {
        reservation.setId(id);
        reservationRepository.save(reservation);
        redirectAttributes.addFlashAttribute("success", "Réservation mise à jour avec succès");
        return "redirect:/admin/reservations";
    }

    @GetMapping("/{id}/delete")
    public String deleteReservation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reservationRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Réservation supprimée avec succès");
        return "redirect:/admin/reservations";
    }

    @GetMapping("/{id}/annuler")
    public String annulerReservation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reservationRepository.findById(id).ifPresent(reservation -> {
            reservation.annuler();
            reservationRepository.save(reservation);
        });
        redirectAttributes.addFlashAttribute("success", "Réservation annulée avec succès");
        return "redirect:/admin/reservations";
    }
}