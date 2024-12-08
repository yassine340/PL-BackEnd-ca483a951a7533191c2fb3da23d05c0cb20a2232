package com.plants.projet_des_plants.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Data
@Entity
@Getter
@Setter
public class AdresseCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String prenom;
    private String nom;
    private String email;
    private String numtel;
    private String adresse;
    private String city;
    private String state;
    private String pincode;
}
