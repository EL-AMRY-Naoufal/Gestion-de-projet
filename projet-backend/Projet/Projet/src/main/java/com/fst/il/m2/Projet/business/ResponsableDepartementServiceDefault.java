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

        // Check if the responsable has the required role
        if (!responsable.hasRole(Role.CHEF_DE_DEPARTEMENT)) {
            throw new RuntimeException("Only Responsable de Département can create users");
        }

        // Save the new user
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
    public Optional<User> getUsersByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Optional<User> getUsersByRole(Role role) {
        return userRepository.findUserByRoles(role);
    }
    @Override
    public User updateUser(Long id, User user, Long responsableId) {
        // Check if the responsable has the required role
        User responsable = userRepository.findById(responsableId)
                .orElseThrow(() -> new RuntimeException("Responsable not found"));

        if (!responsable.hasRole(Role.CHEF_DE_DEPARTEMENT)) {
            throw new RuntimeException("Only Responsable de Département can update users");
        }

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update user information
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        existingUser.setRoles(user.getRoles());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id, Long responsableId) {
        // Check if the responsable has the required role
        User responsable = userRepository.findById(responsableId)
                .orElseThrow(() -> new RuntimeException("Responsable not found"));

        if (!responsable.hasRole(Role.CHEF_DE_DEPARTEMENT)) {
            throw new RuntimeException("Only Responsable de Département can delete users");
        }

        userRepository.deleteById(id);
    }
}
