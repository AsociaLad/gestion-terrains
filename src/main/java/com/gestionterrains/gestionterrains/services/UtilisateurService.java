package com.gestionterrains.gestionterrains.services;

import com.gestionterrains.gestionterrains.models.Utilisateur;
import com.gestionterrains.gestionterrains.enums.Role;
import com.gestionterrains.gestionterrains.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UtilisateurService(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> findById(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Optional<Utilisateur> findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    public Utilisateur save(Utilisateur utilisateur) {
        // Ne pas hasher à nouveau le mot de passe si c'est un utilisateur existant
        if (utilisateur.getId() == null || utilisateur.getMotDePasse() == null || 
            !utilisateur.getMotDePasse().startsWith("$2a$")) {
            utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        }
        return utilisateurRepository.save(utilisateur);
    }

    public void deleteById(Long id) {
        utilisateurRepository.deleteById(id);
    }

    public boolean emailExists(String email) {
        return utilisateurRepository.findByEmail(email).isPresent();
    }
}