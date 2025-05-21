package com.gestionterrains.gestionterrains.services;

import com.gestionterrains.gestionterrains.models.Ville;
import com.gestionterrains.gestionterrains.repositories.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VilleService {

    private final VilleRepository villeRepository;

    @Autowired
    public VilleService(VilleRepository villeRepository) {
        this.villeRepository = villeRepository;
    }

    public List<Ville> findAll() {
        return villeRepository.findAll();
    }

    public Optional<Ville> findById(Long id) {
        return villeRepository.findById(id);
    }

    public Ville save(Ville ville) {
        return villeRepository.save(ville);
    }

    public void deleteById(Long id) {
        villeRepository.deleteById(id);
    }
}