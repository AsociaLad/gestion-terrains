package com.gestionterrains.gestionterrains.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipes")
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private String niveau;

    @Positive(message = "Le nombre de joueurs doit être positif")
    private int nombreJoueurs;

    @ManyToOne
    @JoinColumn(name = "dirigeant_id")
    private Utilisateur dirigeant;

    @ManyToMany
    @JoinTable(
        name = "equipe_matches",
        joinColumns = @JoinColumn(name = "equipe_id"),
        inverseJoinColumns = @JoinColumn(name = "match_id")
    )
    private List<Match> matches = new ArrayList<>();

    public Equipe() {
    }

    public Equipe(String nom, String niveau, int nombreJoueurs, Utilisateur dirigeant) {
        this.nom = nom;
        this.niveau = niveau;
        this.nombreJoueurs = nombreJoueurs;
        this.dirigeant = dirigeant;
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

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public int getNombreJoueurs() {
        return nombreJoueurs;
    }

    public void setNombreJoueurs(int nombreJoueurs) {
        this.nombreJoueurs = nombreJoueurs;
    }

    public Utilisateur getDirigeant() {
        return dirigeant;
    }

    public void setDirigeant(Utilisateur dirigeant) {
        this.dirigeant = dirigeant;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}