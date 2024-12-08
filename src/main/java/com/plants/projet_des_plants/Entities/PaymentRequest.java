package com.plants.projet_des_plants.Entities;

public class PaymentRequest {
    private Long amount; // Montant en centimes
    private String currency;

    // Getters et setters
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
