package com.plants.projet_des_plants.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    public PaymentIntent createPaymentIntent(Long amount, String currency) throws StripeException {
        // Configure les paramètres du paiement
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(amount) // Montant en centimes
                        .setCurrency(currency) // Devise
                        .addPaymentMethodType("card") // Utilise addPaymentMethodType au lieu de set
                        .build();

        // Crée et retourne un PaymentIntent
        return PaymentIntent.create(params);
    }
}
