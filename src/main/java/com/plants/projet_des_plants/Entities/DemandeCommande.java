package com.plants.projet_des_plants.Entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DemandeCommande {

    private String prenom;
    private String nom;
    private String email;
    private String numtel;
    private String adresse;
    private String city;
    private String state;
    private String pincode;
    private String typEPayment;
}
