package com.plants.projet_des_plants.Service;

import com.plants.projet_des_plants.Entities.*;
import com.plants.projet_des_plants.Repository.CartRepository;
import com.plants.projet_des_plants.Repository.ProduitCommandeRepository;
import com.plants.projet_des_plants.utils.StatueCommande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CommandeServiceImpl implements CommandeService{

    @Autowired
    private ProduitCommandeRepository produitCommandeRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UtilisateurService utilisateurService;
    @Override
    public void saveCommande(Long utilisateurId, DemandeCommande demandeCommande) {
        // Vérifiez que l'utilisateur existe
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(utilisateurId);
        if (utilisateur == null) {
            throw new IllegalArgumentException("Utilisateur non trouvé avec l'ID : " + utilisateurId);
        }

        // Continuez avec la logique existante
        List<Cart> carts = cartRepository.findByUtilisateurId(utilisateurId);
        for (Cart cart : carts) {
            ProduitCommande commande = new ProduitCommande();
            commande.setCommandeId(UUID.randomUUID().toString());
            commande.setDateCommande(new Date());
            commande.setProduit(cart.getProduit());
            commande.setPrix(cart.getProduit().getPrix());
            commande.setQuantite(cart.getQuantite());
            commande.setUtilisateur(utilisateur); // Associez l'utilisateur
            commande.setStatue(StatueCommande.IN_PROGRESS.name());
            commande.setTypePayment(demandeCommande.getTypEPayment());

            AdresseCommande adresse = new AdresseCommande();
            adresse.setPrenom(demandeCommande.getPrenom());
            adresse.setNom(demandeCommande.getNom());
            adresse.setEmail(demandeCommande.getEmail());
            adresse.setNumtel(demandeCommande.getNumtel());
            adresse.setAdresse(demandeCommande.getAdresse());
            adresse.setCity(demandeCommande.getCity());
            adresse.setState(demandeCommande.getState());
            adresse.setPincode(demandeCommande.getPincode());

            commande.setAdresseCommande(adresse);

            produitCommandeRepository.save(commande);
        }
        // Videz le panier après validation de la commande
        cartRepository.deleteAll(carts);
    }
    @Override
    public List<ProduitCommande> getHistoriqueCommandes(Long utilisateurId) {
        return produitCommandeRepository.findByUtilisateurId(utilisateurId);
    }

}
