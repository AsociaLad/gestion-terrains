package com.gestionterrains.gestionterrains.config;

import com.gestionterrains.gestionterrains.enums.Role;
import com.gestionterrains.gestionterrains.enums.TypeSurface;
import com.gestionterrains.gestionterrains.models.Terrain;
import com.gestionterrains.gestionterrains.models.Utilisateur;
import com.gestionterrains.gestionterrains.models.Ville;
import com.gestionterrains.gestionterrains.repositories.TerrainRepository;
import com.gestionterrains.gestionterrains.repositories.UtilisateurRepository;
import com.gestionterrains.gestionterrains.repositories.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final VilleRepository villeRepository;
    private final TerrainRepository terrainRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(VilleRepository villeRepository, TerrainRepository terrainRepository,
                           UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.villeRepository = villeRepository;
        this.terrainRepository = terrainRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Créer un utilisateur admin si aucun n'existe
        if (utilisateurRepository.count() == 0) {
            Utilisateur admin = new Utilisateur(
                    "Admin",
                    "Admin",
                    "admin@example.com",
                    passwordEncoder.encode("admin123"),
                    Role.ADMIN,
                    "0600000000"
            );
            utilisateurRepository.save(admin);

            Utilisateur utilisateur = new Utilisateur(
                    "Utilisateur",
                    "Test",
                    "user@example.com",
                    passwordEncoder.encode("user123"),
                    Role.UTILISATEUR,
                    "0611111111"
            );
            utilisateurRepository.save(utilisateur);
        }

        // Créer quelques villes et terrains de test
        if (villeRepository.count() == 0) {
            // Créer des villes
            Ville paris = new Ville("Paris", "75000", "Île-de-France", "Capitale de la France");
            Ville lyon = new Ville("Lyon", "69000", "Auvergne-Rhône-Alpes", "Ville des lumières");
            Ville marseille = new Ville("Marseille", "13000", "Provence-Alpes-Côte d'Azur", "Cité phocéenne");

            villeRepository.save(paris);
            villeRepository.save(lyon);
            villeRepository.save(marseille);

            // Créer des terrains
            Terrain terrain1 = new Terrain(
                    "Stade de France",
                    "93200 Saint-Denis",
                    TypeSurface.GAZON_NATUREL,
                    80000,
                    true,
                    paris
            );

            Terrain terrain2 = new Terrain(
                    "Parc des Princes",
                    "75016 Paris",
                    TypeSurface.GAZON_NATUREL,
                    48000,
                    true,
                    paris
            );

            Terrain terrain3 = new Terrain(
                    "Groupama Stadium",
                    "69500 Décines-Charpieu",
                    TypeSurface.GAZON_SYNTHETIQUE,
                    59000,
                    true,
                    lyon
            );

            Terrain terrain4 = new Terrain(
                    "Stade Vélodrome",
                    "13008 Marseille",
                    TypeSurface.GAZON_NATUREL,
                    67000,
                    true,
                    marseille
            );

            terrainRepository.save(terrain1);
            terrainRepository.save(terrain2);
            terrainRepository.save(terrain3);
            terrainRepository.save(terrain4);
        }
    }
}