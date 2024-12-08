package com.plants.projet_des_plants.Service;


import com.plants.projet_des_plants.Entities.Produit;
import com.plants.projet_des_plants.Repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {
    @Autowired
    private ProduitRepository produitRepository;


    public Produit saveProduit(Produit produit, MultipartFile imageFile) throws IOException {
        produit.setImageName(imageFile.getOriginalFilename());
        produit.setImageType(imageFile.getContentType());
        produit.setImageDate(imageFile.getBytes());
        return produitRepository.save(produit);
    }


    public Produit getProduitById(Long id) {
        Optional<Produit> produit = produitRepository.findById(id);
        return produit.orElse(null); // Retourne le produit ou null si non trouvé
    }


    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }


    public Produit updateProduit(Long id, Produit produit) {
        if (produitRepository.existsById(id)) {
            produit.setId(id);
            return produitRepository.save(produit);
        }
        return null;
    }


    public void deleteProduit(Long id) {
        if (produitRepository.existsById(id)) {
            produitRepository.deleteById(id);
        }
    }

    public List<Produit> getProduitsByCategorieId(Long categorieId) {
        return produitRepository.findByCategorieId(categorieId);
    }
}
