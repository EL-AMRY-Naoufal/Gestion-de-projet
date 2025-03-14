package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.models.PasswordSetToken;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.repositories.PasswordSetTokenRepository;
import com.fst.il.m2.Projet.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Tag(name = "Password Set Controller", description = "Controller for setting user passwords")
public class PasswordSetController {

    @Autowired
    private PasswordSetTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Set a new password", description = "This method allows setting a new password using a token.")
    @ApiResponse(responseCode = "200", description = "Password successfully set")
    @ApiResponse(responseCode = "400", description = "Token has expired or is invalid")
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