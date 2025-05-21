package com.gestionterrains.gestionterrains.models;

import com.gestionterrains.gestionterrains.enums.StatutReservation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@Entity
@Table(name = "paiements")
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive(message = "Le montant doit être positif")
    private double montant;

    @NotNull
    private LocalDateTime datePaiement = LocalDateTime.now();

    private String modePaiement;

    private String referenceTransaction;

    private boolean estConfirme = false;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public Paiement() {
    }

    public Paiement(double montant, String modePaiement, Reservation reservation) {
        this.montant = montant;
        this.modePaiement = modePaiement;
        this.reservation = reservation;
    }

    // Méthode pour confirmer le paiement
    public void confirmerPaiement() {
        this.estConfirme = true;
        if (this.reservation != null && this.reservation.getStatut() != null) {
            this.reservation.setStatut(StatutReservation.CONFIRMEE);
        }
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDateTime getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDateTime datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public String getReferenceTransaction() {
        return referenceTransaction;
    }

    public void setReferenceTransaction(String referenceTransaction) {
        this.referenceTransaction = referenceTransaction;
    }

    public boolean isEstConfirme() {
        return estConfirme;
    }

    public void setEstConfirme(boolean estConfirme) {
        this.estConfirme = estConfirme;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}