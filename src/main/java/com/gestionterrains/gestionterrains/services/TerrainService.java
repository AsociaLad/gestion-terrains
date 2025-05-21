package com.gestionterrains.gestionterrains.services;

import com.gestionterrains.gestionterrains.models.Terrain;
import com.gestionterrains.gestionterrains.repositories.TerrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TerrainService {

    private final TerrainRepository terrainRepository;

    @Autowired
    public TerrainService(TerrainRepository terrainRepository) {
        this.terrainRepository = terrainRepository;
    }

    public List<Terrain> findAll() {
        return terrainRepository.findAll();
    }

    public Optional<Terrain> findById(Long id) {
        return terrainRepository.findById(id);
    }

    public Terrain save(Terrain terrain) {
        return terrainRepository.save(terrain);
    }

    public void deleteById(Long id) {
        terrainRepository.deleteById(id);
    }

    public List<Terrain> findByVilleId(Long villeId) {
        return terrainRepository.findByVilleId(villeId);
    }

    public List<Terrain> findByEstDisponible(boolean estDisponible) {
        return terrainRepository.findByEstDisponible(estDisponible);
    }
}