package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.exceptions.NotFoundException;
import com.fst.il.m2.Projet.models.Module;
import com.fst.il.m2.Projet.models.*;
import com.fst.il.m2.Projet.models.*;
import com.fst.il.m2.Projet.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private EnseignantService enseignantService;

    @Override
    public User createUser(User user, Long responsableId, boolean associateEnseignantWithUser, Long currentYear) {
        User responsable = userRepository.findById(responsableId)
                .orElseThrow(() -> new RuntimeException("Responsable not found"));

        // Check if the responsable has the required role
        if (!responsable.hasRoleForYear(currentYear, Role.CHEF_DE_DEPARTEMENT)) {
            throw new RuntimeException("Only Responsable de Département can create users");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User newUser = null;

        // Ensure the user has roles for specific years (Example: 2023, 2024)
        if (!user.getRoles().isEmpty()) {

            // Save the user directly for roles other than ENSEIGNANT
            newUser = userRepository.save(user);

            if (user.hasRoleForYear(currentYear, Role.CHEF_DE_DEPARTEMENT)) {
                ResponsableDepartement responsableDepartement = new ResponsableDepartement();
                responsableDepartement.setUser(newUser);
                responsableDepartementRepository.save(responsableDepartement);
            } else if (user.hasRoleForYear(currentYear, Role.RESPONSABLE_DE_FORMATION)) {
                ResponsableFormation responsableFormation = new ResponsableFormation();
                responsableFormation.setUser(newUser);
                responsableFormationRepository.save(responsableFormation);
            }

            if (associateEnseignantWithUser) {
                // Create and associate User with Enseignant
                newUser = userRepository.save(user);

                Enseignant enseignant = this.enseignantService.getEnseignantsWithSameUserNameAndFirstName(newUser.getName(), newUser.getFirstname()).get(0);
                enseignant.setUser(newUser);
                enseignant.setHasAccount(true);
                enseignantRepository.save(enseignant);
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
        return userRepository.findUsersByUsername(username);
    }

    @Override
    public List<UserRole> getRolesByUserIdAndYear(Long userId, Long year) {
        return userRoleRepository.findByUserIdAndYearId(userId, year);
    }

    @Override
    @Transactional
    public User updateUser(Long id, User user, Long responsableId, Long currentYear) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        // Vérification et suppression de l'enseignant si nécessaire
        if (existingUser.hasRoleForYear(currentYear, Role.ENSEIGNANT)
                && !user.hasRoleForYear(currentYear, Role.ENSEIGNANT)) {
            this.enseignantRepository.deleteByUser(existingUser);
        }

        // Mise à jour des informations de l'utilisateur
        existingUser.setUsername(user.getUsername());
        existingUser.setName(user.getName());
        existingUser.setFirstname(user.getFirstname());
        existingUser.setEmail(user.getEmail());

        // Récupération des rôles actuels pour l'année en cours
        List<UserRole> existingRoles = userRoleRepository.findByUserIdAndYearId(id, currentYear);
        Map<Role, UserRole> existingRolesMap = existingRoles.stream()
                .collect(Collectors.toMap(UserRole::getRole, Function.identity()));

        // Nouveaux rôles à ajouter
        List<UserRole> newRoles = user.getRoles().stream()
                .filter(ur -> ur.getYear().getId().equals(currentYear)) // Filtrer sur l'année en cours
                .filter(ur -> !existingRolesMap.containsKey(ur.getRole())) // Vérifier si le rôle est déjà présent
                .peek(ur -> ur.setUser(existingUser)) // Associer l'utilisateur existant
                .collect(Collectors.toList());

        // Rôles à supprimer (qui ne sont plus présents dans la nouvelle liste)
        List<UserRole> rolesToDelete = existingRoles.stream()
                .filter(existingRole -> user.getRoles().stream()
                        .noneMatch(newRole -> newRole.getRole().equals(existingRole.getRole())))
                .collect(Collectors.toList());

        // Suppression des rôles obsolètes
        if (!rolesToDelete.isEmpty()) {
            userRoleRepository.deleteAll(rolesToDelete);
            userRoleRepository.flush(); // Forcer la suppression avant d'ajouter les nouveaux rôles
        }

        // Ajout des nouveaux rôles
        if (!newRoles.isEmpty()) {
            userRoleRepository.saveAll(newRoles);
        }

        // Mise à jour des roles de l'utilisateur
        existingUser.getRoles().addAll(newRoles);
        existingUser.getRoles().removeAll(rolesToDelete);

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
        if (user.hasRole(Role.ENSEIGNANT)) {
            enseignantRepository.deleteByUser(user);
        } else if (user.hasRole(Role.CHEF_DE_DEPARTEMENT)) {
            responsableDepartementRepository.deleteByUser(user);
        } else if (user.hasRole(Role.RESPONSABLE_DE_FORMATION)) {
            responsableFormationRepository.deleteByUser(user);
        }
        // Delete the user from the User table
        userRepository.deleteById(id);
    }


    public List<UserRole> getUsersByRole(Role role) {
        return userRoleRepository.findByRole(role);
    }
    public List<UserRole> getUsersByRoleAndYear(Role role, Long year) {
        return userRoleRepository.findByRoleAndYearId(role, year);
    }

    }
