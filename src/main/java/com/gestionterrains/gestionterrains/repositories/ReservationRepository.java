package com.gestionterrains.gestionterrains.repositories;

import com.gestionterrains.gestionterrains.models.Reservation;
import com.gestionterrains.gestionterrains.enums.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUtilisateurId(Long utilisateurId);
    List<Reservation> findByTerrainId(Long terrainId);
    List<Reservation> findByStatut(StatutReservation statut);
    List<Reservation> findByDateDebutBetween(LocalDateTime debut, LocalDateTime fin);
    long countByStatut(StatutReservation statut);
}