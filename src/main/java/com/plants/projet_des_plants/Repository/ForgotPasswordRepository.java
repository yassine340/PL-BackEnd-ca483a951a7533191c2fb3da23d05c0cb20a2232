package com.plants.projet_des_plants.Repository;

import com.plants.projet_des_plants.Entities.ForgetPassword;
import com.plants.projet_des_plants.Entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgetPassword,Integer> {
    @Query("select fp from ForgetPassword  fp where fp.otp= ?1 and fp.utilisateur=?2")
    Optional<ForgetPassword> findByOtpAndUser(Integer otp, Utilisateur utilisateur);
}
