package com.plants.projet_des_plants.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import lombok.*;

import java.util.List;

@Entity @NoArgsConstructor @AllArgsConstructor
@Data

public class Categorie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    // Utiliser @JsonManagedReference pour la gestion de la référence dans la sérialisation
    @JsonManagedReference
    @OneToMany(mappedBy = "categorie")
    private List<Produit> plantes;



}
