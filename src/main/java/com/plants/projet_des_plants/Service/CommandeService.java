package com.plants.projet_des_plants.Service;

import com.plants.projet_des_plants.Entities.DemandeCommande;
import com.plants.projet_des_plants.Entities.ProduitCommande;

import java.util.List;

public interface CommandeService {
    public void saveCommande(Long utilisateur, DemandeCommande demandeCommande);
    List<ProduitCommande> getHistoriqueCommandes(Long utilisateurId); // Nouvelle méthode
}
