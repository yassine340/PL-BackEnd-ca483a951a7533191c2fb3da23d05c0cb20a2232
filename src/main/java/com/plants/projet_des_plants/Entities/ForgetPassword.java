package com.plants.projet_des_plants.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ForgetPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fpid;
    @Column(nullable = true)
    private Integer otp;

    private Date expirationTime;
    @OneToOne
    @JsonIgnore
    private Utilisateur utilisateur;

}
