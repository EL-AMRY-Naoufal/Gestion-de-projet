package com.fst.il.m2.Projet.Controllers;

import com.fst.il.m2.Projet.models.PasswordSetToken;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.repositories.PasswordSetTokenRepository;
import com.fst.il.m2.Projet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class PasswordSetController {

    @Autowired
    private PasswordSetTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/setPassword")
    public ResponseEntity<String> setPassword(@RequestParam("token") String token,
                                                @RequestParam("newPassword") String newPassword) {
        PasswordSetToken setToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (setToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token has expired");
        }

        User user = setToken.getUser();
        user.setPassword(newPassword);  // Encrypt this password before saving
        userRepository.save(user);

        return ResponseEntity.ok("Password successfully set");
    }
}