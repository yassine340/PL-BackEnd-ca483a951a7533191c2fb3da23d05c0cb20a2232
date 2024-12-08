package com.plants.projet_des_plants.Web;

import com.plants.projet_des_plants.Entities.*;
import com.plants.projet_des_plants.Service.CartService;
import com.plants.projet_des_plants.Service.CommandeService;
import com.plants.projet_des_plants.Service.PaymentService;
import com.plants.projet_des_plants.Service.UtilisateurService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin(origins = "http://localhost:4200")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private CartService cartService;
    @Autowired
private CommandeService commandeService;
    @Autowired
    private PaymentService paymentService;
    @GetMapping("/byEmail")
    public ResponseEntity<Utilisateur> getUserByEmail(@RequestParam String email) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(email);
        if (utilisateur != null) {
            return ResponseEntity.ok(utilisateur);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Utilisateur utilisateur) {
        Utilisateur newUser = utilisateurService.registerUtilisateur(utilisateur);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Utilisateur utilisateur) {
        Utilisateur loggedInUser = utilisateurService.login(utilisateur.getEmail(), utilisateur.getPassword());

        if (loggedInUser != null) {
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    @PostMapping("/addCart")
    public ResponseEntity<?> addToCart(@RequestParam Long idProduit, @RequestParam Long idUtilisateur) {
        Cart saveCart = cartService.saveCart(idProduit, idUtilisateur);
        if (ObjectUtils.isEmpty(saveCart)) {
            return ResponseEntity.status(400).body("Product not added to cart");
        } else {
            return ResponseEntity.ok(saveCart);
        }
    }



    @GetMapping("/{idUtilisateur}/cart")
    public ResponseEntity<?> getCart(@PathVariable Long idUtilisateur) {
        List<Cart> carts = cartService.getCartByUtilisateur(idUtilisateur);
        if (ObjectUtils.isEmpty(carts)) {
            return ResponseEntity.status(404).body("No items found in the cart");
        }
        return ResponseEntity.ok(carts);
    }

    @PostMapping("/updateCartQuantite")
    public ResponseEntity<String> updateCartQuantite(@RequestParam String action, @RequestParam Long cartId) {
        cartService.updateCartQuantity(cartId, action); // Assuming this method handles both increase/decrease logic
        return ResponseEntity.ok("Cart updated successfully");
    }
    @DeleteMapping("/deleteCart/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id) {
        cartService.deleteCartItem(id);
        return ResponseEntity.ok("Cart deleted successfully");
    }


    private Utilisateur getLoggedInUserDetails(Principal p) {
        String email = p.getName();
        return utilisateurService.getUtilisateurByEmail(email);
    }
    @GetMapping("/orders")
    public String orderPage(){
        return "/user/order";
    }

    @PostMapping("/save-commande")
    public ResponseEntity<?> saveCommande(@RequestBody DemandeCommande request, @RequestParam Long idUtilisateur) {
        // Appelle directement le service avec l'ID utilisateur
        commandeService.saveCommande(idUtilisateur, request);
        return ResponseEntity.ok("Commande enregistrée avec succès");
    }

    @GetMapping("/{id}/commandes")
    public ResponseEntity<List<ProduitCommande>> getHistoriqueCommandes(@PathVariable Long id) {
        List<ProduitCommande> commandes = commandeService.getHistoriqueCommandes(id);
        return ResponseEntity.ok(commandes);
    }

    // Ajout de la méthode createPaymentIntent dans UtilisateurController
    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentRequest paymentRequest) {
        try {
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(
                    paymentRequest.getAmount(),
                    paymentRequest.getCurrency()
            );

            // Retourne le client_secret au frontend
            return ResponseEntity.ok(Map.of("clientSecret", paymentIntent.getClientSecret()));
        } catch (StripeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Utilisateur>> getAllUsers() {
        List<Utilisateur> utilisateurs = utilisateurService.getAllUsers();
        if (utilisateurs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(utilisateurs);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUtilisateur(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
        if (utilisateur == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur not found");
        }
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.ok("Utilisateur deleted successfully");
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    @PutMapping("/{id}/updateRole")
    public ResponseEntity<String> updateRole(@PathVariable Long id, @RequestParam String role) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
        if (utilisateur == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur not found");
        }
        utilisateur.setRole(role);
        utilisateurService.saveUtilisateur(utilisateur);
        return ResponseEntity.ok("Role updated successfully");
    }
    @PutMapping("/{id}/updateDetails")
    public ResponseEntity<String> updateDetails(
            @PathVariable Long id,
            @RequestParam String username,
            @RequestParam String email
    ) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
        if (utilisateur == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur not found");
        }
        utilisateur.setUsername(username);
        utilisateur.setEmail(email);
        utilisateurService.saveUtilisateur(utilisateur);
        return ResponseEntity.ok("Details updated successfully");
    }
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUserById(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
        if (utilisateur != null) {
            return ResponseEntity.ok(utilisateur);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}