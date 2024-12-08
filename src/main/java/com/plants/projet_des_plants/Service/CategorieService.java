package com.plants.projet_des_plants.Service;

import com.plants.projet_des_plants.Entities.Categorie;
import com.plants.projet_des_plants.Repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    public Categorie saveCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public Categorie getCategorieById(Long id) {
        return categorieRepository.findById(id).orElse(null);
    }

    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    public Categorie updateCategorie(Long id, Categorie categorie) {
        Optional<Categorie> existingCategorie = categorieRepository.findById(id);
        if (existingCategorie.isPresent()) {
            Categorie updatedCategorie = existingCategorie.get();
            updatedCategorie.setNom(categorie.getNom());
            return categorieRepository.save(updatedCategorie);
        } else {
            return null;
        }
    }

    public void deleteCategorie(Long id) {
        categorieRepository.deleteById(id);
    }
}
