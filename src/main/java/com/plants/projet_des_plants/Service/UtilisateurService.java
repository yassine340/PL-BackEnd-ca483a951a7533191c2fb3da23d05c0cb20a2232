package com.plants.projet_des_plants.Service;

import com.plants.projet_des_plants.Entities.Utilisateur;
import com.plants.projet_des_plants.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Utilisateur registerUtilisateur(Utilisateur utilisateur) {
        utilisateur.setRole("utilisateur");
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur login(String email, String password) {
        return utilisateurRepository.findByEmail(email)
                .filter(utilisateur -> passwordEncoder.matches(password, utilisateur.getPassword()))
                .orElse(null);
    }

    public Utilisateur getUtilisateurByEmail(String email) {
        return utilisateurRepository.findByEmail(email).orElse(null);
    }
    // Nouvelle méthode pour récupérer un utilisateur par ID
    public Utilisateur getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id).orElse(null);
    }
    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }
    public void deleteUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }
    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

}
