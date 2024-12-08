package com.plants.projet_des_plants.Repository;

import com.plants.projet_des_plants.Entities.Utilisateur;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
    Optional<Utilisateur> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update Utilisateur u set u.password =?2 where u.email =?1")
    void updatePassword(String email,String password);

}
