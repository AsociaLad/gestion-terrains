package com.gestionterrains.gestionterrains.models;

import com.gestionterrains.gestionterrains.enums.TypeSurface;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "terrains")
public class Terrain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le type de surface est obligatoire")
    private TypeSurface typeSurface;

    @Positive(message = "La capacité doit être positive")
    private int capacite;

    private boolean estDisponible = true;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "ville_id")
    @NotNull(message = "La ville est obligatoire")
    private Ville ville;

    @OneToMany(mappedBy = "terrain", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    public Terrain() {
    }

    public Terrain(String nom, String adresse, TypeSurface typeSurface, int capacite, boolean estDisponible, Ville ville) {
        this.nom = nom;
        this.adresse = adresse;
        this.typeSurface = typeSurface;
        this.capacite = capacite;
        this.estDisponible = estDisponible;
        this.ville = ville;
    }

    // Méthode pour vérifier si le terrain est disponible pour une période donnée
    public boolean estDisponiblePourReservation(LocalDateTime debut, LocalDateTime fin) {
        if (!estDisponible) {
            return false;
        }
        
        // Vérifier si le terrain n'est pas déjà réservé pour la période demandée
        for (Reservation reservation : reservations) {
            if (reservation.getStatut() != null && !reservation.getStatut().equals("ANNULEE")) {
                if (!(fin.isBefore(reservation.getDateDebut()) || debut.isAfter(reservation.getDateFin()))) {
                    return false;
                }
            }
        }
        return true;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public TypeSurface getTypeSurface() {
        return typeSurface;
    }

    public void setTypeSurface(TypeSurface typeSurface) {
        this.typeSurface = typeSurface;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public boolean isEstDisponible() {
        return estDisponible;
    }

    public void setEstDisponible(boolean estDisponible) {
        this.estDisponible = estDisponible;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}