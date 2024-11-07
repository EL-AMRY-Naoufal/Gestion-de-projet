package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResponsableDepartementServiceDefault implements ResponsableDepartementService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordSetServiceDefault passwordSetServiceDefault;

    @Override
    public User createUser(User user, Long responsableId) {
        User responsable = userRepository.findById(responsableId)
                .orElseThrow(() -> new RuntimeException("Responsable not found"));

        if (!responsable.getRole().equals(Role.CHEF_DE_DEPARTEMENT)) {
            throw new RuntimeException("Only Responsable de Département can create users");
        }

        User newUser = userRepository.save(user);

        // Generate a token and send email for setting the password
        String token = UUID.randomUUID().toString();
        passwordSetServiceDefault.createPasswordSetTokenForUser(newUser, token);
        passwordSetServiceDefault.sendPasswordSetEmail(newUser, token);

        return newUser;
    }


    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User user, Long responsableId) {
        // Check if the authenticated user is a Responsable de Département
        User responsable = userRepository.findById(responsableId)
                .orElseThrow(() -> new RuntimeException("Responsable not found"));

        if (!responsable.getRole().equals(Role.CHEF_DE_DEPARTEMENT)) {
            throw new RuntimeException("Only Responsable de Département can update users");
        }

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id, Long responsableId) {
        // Check if the authenticated user is a Responsable de Département
        User responsable = userRepository.findById(responsableId)
                .orElseThrow(() -> new RuntimeException("Responsable not found"));

        if (!responsable.getRole().equals(Role.CHEF_DE_DEPARTEMENT)) {
            throw new RuntimeException("Only Responsable de Département can delete users");
        }

        userRepository.deleteById(id);
    }
}
