package com.plants.projet_des_plants.utils;

import lombok.Getter;
import lombok.Setter;

public enum StatueCommande {
    IN_PROGRESS (1, "In Progress"),
    ORDER_RECIVED (2, "Order Recived"),
    PRODUCT_PACKED (3, "Product Packed"),
    OUT_FOR_DELIVERY (4, "Out for Delivery"),
    DELIVERED (5, "Delivered");
    @Setter
    @Getter
    private Integer id;
    private String nom;


    private StatueCommande(Integer id, String nom){
        this.id=id;
        this.nom=nom;
    }

}
