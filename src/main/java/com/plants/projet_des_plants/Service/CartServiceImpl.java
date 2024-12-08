package com.plants.projet_des_plants.Service;

import com.plants.projet_des_plants.Entities.Cart;
import com.plants.projet_des_plants.Entities.Produit;
import com.plants.projet_des_plants.Entities.Utilisateur;
import com.plants.projet_des_plants.Repository.CartRepository;
import com.plants.projet_des_plants.Repository.ProduitRepository;
import com.plants.projet_des_plants.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Override
    public Cart saveCart(Long idProduit, Long idUtilisateur) {
        Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur).orElseThrow(() -> new RuntimeException("Utilisateur not found"));
        Produit produit = produitRepository.findById(idProduit).orElseThrow(() -> new RuntimeException("Produit not found"));

        Cart cartStatus = cartRepository.findByUtilisateurIdAndProduitId(idUtilisateur, idProduit);

        Cart cart = null;

        if (cartStatus == null) {
            cart = new Cart();
            cart.setProduit(produit);
            cart.setUtilisateur(utilisateur);
            cart.setQuantite(1);
            cart.setPrixTotal(produit.getPrix()); // Initial price when quantity is 1
            cartRepository.save(cart);
        } else {
            cart = cartStatus;
            cart.setQuantite(cart.getQuantite() + 1);
            cart.setPrixTotal(cart.getQuantite() * produit.getPrix());
            cartRepository.save(cart);
        }

        return cart;
    }

    @Override
    public List<Cart> getCartByUtilisateur(Long idUtilisateur) {
        List<Cart> carts = cartRepository.findByUtilisateurId(idUtilisateur);

        int totalOrderPrice = 0;
        for (Cart c : carts) {
            int prixTotal = c.getProduit().getPrix() * c.getQuantite();
            c.setPrixTotal(prixTotal);
            totalOrderPrice += prixTotal;
        }

        return carts; // No need to set totalOrderPrice in individual cart items here, total can be handled elsewhere.
    }

    @Override
    public Integer getCountCart(Long idUtilisateur) {
        return cartRepository.countByUtilisateurId(idUtilisateur);
    }

    @Override
    public void updateCartQuantite(String sy, Long cid) {

    }

    @Override
    public void updateCartQuantity(Long cartId, String action) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart item not found"));
        int updatedQuantity = cart.getQuantite();

        if ("decrease".equalsIgnoreCase(action)) {
            updatedQuantity = updatedQuantity - 1;
            if (updatedQuantity <= 0) {
                cartRepository.delete(cart); // Remove cart item if quantity is 0 or less
            } else {
                cart.setQuantite(updatedQuantity);
                cart.setPrixTotal(updatedQuantity * cart.getProduit().getPrix());
                cartRepository.save(cart);
            }
        } else if ("increase".equalsIgnoreCase(action)) {
            updatedQuantity = updatedQuantity + 1;
            cart.setQuantite(updatedQuantity);
            cart.setPrixTotal(updatedQuantity * cart.getProduit().getPrix());
            cartRepository.save(cart);
        }
    }

    @Override
    public void deleteCartItem(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartRepository.delete(cart);
    }
}
