package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.*;
import com.fst.il.m2.Projet.models.Module;
import com.fst.il.m2.Projet.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ResponsableDepartementServiceDefault implements ResponsableDepartementService {

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
    @Autowired
    private AffectationRepository affectationRepository;
    @Autowired
    private ModuleRepository moduleRepository;

    @Override
    public User createUser(User user, Long responsableId, boolean associateEnseignantWithUser) {
        User responsable = userRepository.findById(responsableId)
                .orElseThrow(() -> new RuntimeException("Responsable not found"));

        // Check if the responsable has the required role
        if (!responsable.hasRole(Role.CHEF_DE_DEPARTEMENT)) {
            throw new RuntimeException("Only Responsable de Département can create users");
        }

        User newUser = null;

        // Ensure the user has roles for specific years (Example: 2023, 2024)
        if (!user.getRoles().isEmpty()) {
            Map<Integer, Role> userRoles = user.getRoles();

            // Save the user directly for roles other than ENSEIGNANT
            newUser = userRepository.save(user);

            if (userRoles.containsValue(Role.CHEF_DE_DEPARTEMENT)) {
                ResponsableDepartement responsableDepartement = new ResponsableDepartement();
                responsableDepartement.setUser(newUser);
                responsableDepartementRepository.save(responsableDepartement);
            } else if (userRoles.containsValue(Role.RESPONSABLE_DE_FORMATION)) {
                ResponsableFormation responsableFormation = new ResponsableFormation();
                responsableFormation.setUser(newUser);
                responsableFormationRepository.save(responsableFormation);
            }
        } else {
            // Special handling for ENSEIGNANT role
            if (associateEnseignantWithUser) {
                // Create and associate User with Enseignant
                newUser = userRepository.save(user);

                Enseignant enseignant = new Enseignant();
                enseignant.setUser(newUser);
                enseignantRepository.save(enseignant);
            } else {
                // Create Enseignant without associating User
                Enseignant enseignant = new Enseignant();
                enseignantRepository.save(enseignant);
            }
        }

        String token = UUID.randomUUID().toString();
        //passwordSetServiceDefault.createPasswordSetTokenForUser(newUser, token);
        //passwordSetServiceDefault.sendPasswordSetEmail(newUser, token);

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

        // Check and remove from specific role-based tables
        if (user.getRoles().containsValue(Role.ENSEIGNANT)) {
            enseignantRepository.deleteByUser(user);
        } else if (user.getRoles().containsValue(Role.CHEF_DE_DEPARTEMENT)) {
            responsableDepartementRepository.deleteByUser(user);
        } else if (user.getRoles().containsValue(Role.RESPONSABLE_DE_FORMATION)) {
            responsableFormationRepository.deleteByUser(user);
        }

        // Delete the user from the User table
        userRepository.deleteById(id);
    }


    @Override
    public Affectation affecterModuleToEnseignant(Long userId, Long moduleId, int heuresAssignees) {

        // Récupérer l'enseignant depius l'ID de l'utilisateur
        Long enseignantID = enseignantRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId))
                .getId();

        System.err.println("enseignantID: " + enseignantID);

        Enseignant enseignant = enseignantRepository.findById(enseignantID)
                .orElseThrow(() -> new RuntimeException("Enseignant not found with id: " + enseignantID));

        // Récupérer le module
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found with id: " + moduleId));

        // Vérifier si l'enseignant a des heures disponibles
       /* if (enseignant.getHeuresAssignees() + heuresAssignees > enseignant.getMaxHeuresService()) {
            throw new RuntimeException("Heures assignées dépassent le maximum autorisé pour cet enseignant.");
        }*/

        // Créer une nouvelle affectation
        Affectation affectation = new Affectation();
        affectation.setEnseignant(enseignant);
        affectation.setModule(module);
        affectation.setHeuresAssignees(heuresAssignees);
        affectation.setDateAffectation(LocalDate.now());

        // Sauvegarder l'affectation
        Affectation savedAffectation = affectationRepository.save(affectation);

        // Mettre à jour les heures assignées de l'enseignant
        enseignant.setHeuresAssignees(enseignant.getHeuresAssignees() + heuresAssignees);
        enseignantRepository.save(enseignant);

        return savedAffectation;
    }

}
