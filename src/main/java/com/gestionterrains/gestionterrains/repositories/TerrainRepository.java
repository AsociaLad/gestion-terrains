package com.gestionterrains.gestionterrains.repositories;

import com.gestionterrains.gestionterrains.models.Terrain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TerrainRepository extends JpaRepository<Terrain, Long> {
    List<Terrain> findByVilleId(Long villeId);
    List<Terrain> findByEstDisponibleTrue();
    List<Terrain> findByEstDisponible(boolean estDisponible);
}