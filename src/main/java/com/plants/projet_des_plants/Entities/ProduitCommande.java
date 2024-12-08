package com.plants.projet_des_plants.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ProduitCommande {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String CommandeId;
    private Date DateCommande;
    @ManyToOne
    private Produit produit;
    private int prix;
    private int quantite;
    @ManyToOne
    private Utilisateur utilisateur;
    private String Statue;
    private String typePayment;
    @OneToOne(cascade = CascadeType.ALL)
    private AdresseCommande adresseCommande;
}
