package com.gestionterrains.gestionterrains.repositories;

import com.gestionterrains.gestionterrains.models.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VilleRepository extends JpaRepository<Ville, Long> {
    // Méthodes spécifiques si nécessaire
}