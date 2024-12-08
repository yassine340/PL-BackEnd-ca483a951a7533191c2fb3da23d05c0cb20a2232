package com.plants.projet_des_plants.Service;

import com.plants.projet_des_plants.Entities.ProduitCommande;
import com.plants.projet_des_plants.Repository.ProduitCommandeRepository;
import com.plants.projet_des_plants.dto.MailBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class RappelArrosageService {

    private static final Logger logger = LoggerFactory.getLogger(RappelArrosageService.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProduitCommandeRepository produitCommandeRepository;

    @Scheduled(fixedRate = 30000000)
    public void envoyerRappel() {
        logger.info("Méthode envoyerRappel déclenchée");

        List<ProduitCommande> commandes = produitCommandeRepository.findAll();

        for (ProduitCommande commande : commandes) {
            long hoursSinceOrder = Duration.between(commande.getDateCommande().toInstant(), Instant.now()).toHours();

            if (hoursSinceOrder >= 0) {
                String email = commande.getUtilisateur().getEmail();

                MailBody mailBody = MailBody.builder()
                        .to(email)
                        .subject("Rappel : Arrosez votre plante")
                        .text("N'oubliez pas d'arroser votre plante commandée : " + commande.getProduit().getNom())
                        .build();

                emailService.sendSimpleMessage(mailBody);
                logger.info("Email envoyé à {}", email);
            }
        }
    }
}