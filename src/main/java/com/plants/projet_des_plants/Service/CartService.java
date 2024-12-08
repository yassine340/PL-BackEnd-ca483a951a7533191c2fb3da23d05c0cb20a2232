package com.plants.projet_des_plants.Service;

import com.plants.projet_des_plants.Entities.Cart;

import java.util.List;

public interface CartService {
    Cart saveCart(Long idProduit, Long idUtilisateur);
    List<Cart> getCartByUtilisateur(Long idUtilisateur);
    Integer getCountCart(Long idUtilisateur);
    void updateCartQuantite(String sy, Long cid);

    void updateCartQuantity(Long cartId, String action);

    void deleteCartItem(Long cartId);
}

