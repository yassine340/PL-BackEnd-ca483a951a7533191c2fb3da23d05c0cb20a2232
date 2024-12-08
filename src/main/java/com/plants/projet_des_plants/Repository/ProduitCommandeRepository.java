package com.plants.projet_des_plants.Repository;

import com.plants.projet_des_plants.Entities.ProduitCommande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduitCommandeRepository extends JpaRepository<ProduitCommande,Long> {
    List<ProduitCommande> findByUtilisateurId(Long utilisateurId); // Recherche par ID utilisateur

}
