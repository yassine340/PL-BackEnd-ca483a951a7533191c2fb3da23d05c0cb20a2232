package com.plants.projet_des_plants.Entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import lombok.*;

@Entity @NoArgsConstructor @AllArgsConstructor
@Data
public class Produit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;

    private int prix;
    private int quantite;

    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageDate;


    // Utiliser @JsonBackReference pour ignorer cette référence dans la sérialisation
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;
}
