package com.plants.projet_des_plants.Repository;

import com.plants.projet_des_plants.Entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUtilisateurIdAndProduitId(Long idUtilisateur, Long idProduit);
    Integer countByUtilisateurId(Long idUtilisateur);
    List<Cart> findByUtilisateurId(Long idUtilisateur);
}

