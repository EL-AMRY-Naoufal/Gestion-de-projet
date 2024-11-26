package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.ResponsableDepartement;
import com.fst.il.m2.Projet.models.ResponsableFormation;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.repositories.EnseignantRepository;
import com.fst.il.m2.Projet.repositories.ResponsableDepartementRepository;
import com.fst.il.m2.Projet.repositories.ResponsableFormationRepository;
import com.fst.il.m2.Projet.repositories.UserRepository;
import jakarta.transaction.Transactional;
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
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private ResponsableDepartementRepository responsableDepartementRepository;
    @Autowired
    private ResponsableFormationRepository responsableFormationRepository;

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
        //passwordSetServiceDefault.createPasswordSetTokenForUser(newUser, token);
        //passwordSetServiceDefault.sendPasswordSetEmail(newUser, token);

        // Save the user in the specific table based on their role

        if (user.getRoles().contains(Role.ENSEIGNANT)) {
            Enseignant enseignant = new Enseignant();
            enseignant.setUser(newUser);
            enseignantRepository.save(enseignant); // Save in Enseignant table
        } else if (user.getRoles().contains(Role.CHEF_DE_DEPARTEMENT)) {
            ResponsableDepartement responsableDepartement = new ResponsableDepartement();
            responsableDepartement.setUser(newUser);
            responsableDepartementRepository.save(responsableDepartement); // Save in ResponsableDepartement table
        } else if (user.getRoles().contains(Role.RESPONSABLE_DE_FORMATION)) {
            ResponsableFormation responsableFormation = new ResponsableFormation();
            responsableFormation.setUser(newUser);
            responsableFormationRepository.save(responsableFormation); // Save in ResponsableFormation table
        }

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
    public List<User> getUsersByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> getUsersByRole(Role role) {
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
//        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        existingUser.setRoles(user.getRoles());

        return userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id, Long responsableId) {
        // Check if the responsable has the required role
        User responsable = userRepository.findById(responsableId)
                .orElseThrow(() -> new RuntimeException("Responsable not found"));

        if (!responsable.hasRole(Role.CHEF_DE_DEPARTEMENT)) {
            throw new RuntimeException("Only Responsable de Département can delete users");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.hasRole(Role.CHEF_DE_DEPARTEMENT)) {
            throw new RuntimeException("On ne peut pas supprimer le responsable de département");
        }
        System.out.println("dddddddd" + user);

        // Check and remove from specific role-based tables
        if (user.getRoles().contains(Role.ENSEIGNANT)) {
            enseignantRepository.deleteByUser(user);
        } else if (user.getRoles().contains(Role.CHEF_DE_DEPARTEMENT)) {
            responsableDepartementRepository.deleteByUser(user);
        } else if (user.getRoles().contains(Role.RESPONSABLE_DE_FORMATION)) {
            responsableFormationRepository.deleteByUser(user);
        }

        // Delete the user from the User table
        userRepository.deleteById(id);
    }
}
