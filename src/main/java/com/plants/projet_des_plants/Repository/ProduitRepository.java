package com.plants.projet_des_plants.Repository;

import com.plants.projet_des_plants.Entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit,Long> {
    @Query("SELECT p FROM Produit p WHERE p.categorie.id = :categorieId")
    List<Produit> findByCategorieId(Long categorieId);
}
