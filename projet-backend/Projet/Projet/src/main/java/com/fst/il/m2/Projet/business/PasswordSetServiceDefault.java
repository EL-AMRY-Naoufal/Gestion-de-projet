package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.PasswordSetToken;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.repositories.PasswordSetTokenRepository;
import com.fst.il.m2.Projet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PasswordSetServiceDefault implements PasswordSetService {

    @Autowired
    private PasswordSetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    public void createPasswordSetTokenForUser(User user, String token) {
        PasswordSetToken setToken = new PasswordSetToken();
        setToken.setUser(user);
        setToken.setToken(token);
        setToken.setExpiryDate(LocalDateTime.now().plusHours(24));  // Expires in 24 hours
        tokenRepository.save(setToken);
    }

    public void sendPasswordSetEmail(User user, String token) {
        String url = "http://localhost:8080/setPassword?token=" + token;  // Modify as needed
        String message = "Click the link to set your password: " + url;
        emailService.sendEmail(user.getEmail(), "Set Your Password", message);
    }
}