package com.plants.projet_des_plants.Web;

import com.plants.projet_des_plants.Entities.ForgetPassword;
import com.plants.projet_des_plants.Entities.Utilisateur;
import com.plants.projet_des_plants.Repository.ForgotPasswordRepository;
import com.plants.projet_des_plants.Repository.UtilisateurRepository;
import com.plants.projet_des_plants.Service.EmailService;
import com.plants.projet_des_plants.dto.MailBody;
import com.plants.projet_des_plants.utils.ChangePassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/forgetPassword")
public class ForgetPasswordController {

    private final PasswordEncoder passwordEncoder;
    private UtilisateurRepository utilisateurRepository;
    public final EmailService emailService;
    public final ForgotPasswordRepository forgotPasswordRepository;


    public ForgetPasswordController(EmailService emailService,
                                    ForgotPasswordRepository forgotPasswordRepository,
                                    PasswordEncoder passwordEncoder,
                                    UtilisateurRepository utilisateurRepository) {
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.utilisateurRepository = utilisateurRepository; // Correction ici
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }


    //send email for verify
    @PostMapping("/verifyMail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please enter a valid email !!!!"));

        int otp = otpGenerator();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("this is the OTP for your Forget Password request: " + otp)
                .subject("OTP for forget Password request")
                .build();

        ForgetPassword fp = ForgetPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 7000 * 1000))
                .utilisateur(utilisateur) // Modifié ici
                .build();

        emailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(fp);
        return ResponseEntity.ok("Email sent for verification");
    }

    @PostMapping("verify/{otp}/{email}")
    public ResponseEntity<String> verifyOpt(@PathVariable Integer otp, @PathVariable String email){

        Utilisateur utilisateur=utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please and valid email !!!!"));
        ForgetPassword fp =forgotPasswordRepository.findByOtpAndUser(otp,utilisateur)
                .orElseThrow(() -> new RuntimeException("Invalid OTP for email !! " + email));
        if(fp.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>("OTP has expired!", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("OTP verified !!");
    }


    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword,
                                                        @PathVariable String email){
        if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
            return new ResponseEntity<>("Please enter the password again!", HttpStatus.EXPECTATION_FAILED);
        }
        String encoderPassword =passwordEncoder.encode(changePassword.password()) ;
        utilisateurRepository.updatePassword(email,encoderPassword);
        return ResponseEntity.ok("Password has been changes!!");
    }
    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }

}
