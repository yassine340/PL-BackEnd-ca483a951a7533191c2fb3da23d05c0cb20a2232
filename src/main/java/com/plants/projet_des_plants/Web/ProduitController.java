package com.plants.projet_des_plants.Web;


import com.plants.projet_des_plants.Entities.Produit;
import com.plants.projet_des_plants.Service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {
    @Autowired
    private ProduitService produitService;


    @PostMapping("/ajoutProduit")
    public ResponseEntity<Produit> createProduit(
            @RequestPart("produit") Produit produit,
            @RequestPart("Imageile") MultipartFile Imageile) {
        try {
            Produit createdProduit = produitService.saveProduit(produit, Imageile);
            return ResponseEntity.ok(createdProduit);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/image/{productId}")
    public ResponseEntity<byte[]> getImageByproductId(@PathVariable Long productId) {
        Produit produit = produitService.getProduitById(productId);
        if (produit != null && produit.getImageDate() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(produit.getImageType()))
                    .body(produit.getImageDate());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/affiche/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        Produit produit = produitService.getProduitById(id);
        return produit != null ? ResponseEntity.ok(produit) : ResponseEntity.notFound().build();
    }


    @GetMapping("/afficheTous")
    public List<Produit> getAllProduits() {
        return produitService.getAllProduits();
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable Long id, @RequestBody Produit produit) {
        Produit updatedProduit = produitService.updateProduit(id, produit);
        return updatedProduit != null ? ResponseEntity.ok(updatedProduit) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<List<Produit>> getProduitsByCategory(@PathVariable Long id) {
        List<Produit> produits = produitService.getProduitsByCategorieId(id);
        return ResponseEntity.ok(produits);
    }

}
