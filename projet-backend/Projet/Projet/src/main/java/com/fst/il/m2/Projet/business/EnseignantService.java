package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
import com.fst.il.m2.Projet.dto.AffectationDTO;
import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.exceptions.NotFoundException;
import com.fst.il.m2.Projet.exceptions.UnauthorizedException;
import com.fst.il.m2.Projet.models.Affectation;
import com.fst.il.m2.Projet.models.Annee;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.repositories.AffectationRepository;
import com.fst.il.m2.Projet.repositories.EnseignantRepository;
import com.fst.il.m2.Projet.models.UserRole;
import com.fst.il.m2.Projet.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.fst.il.m2.Projet.repositories.UserRepository;
import com.fst.il.m2.Projet.repositories.specifications.EnseignantSpecification;
import com.fst.il.m2.Projet.repositories.specifications.UserSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final AffectationRepository affectationRepository;
    private final UserRepository userRepository;
    private final UserSpecification userSpecifications;
    private final EnseignantSpecification enseignantSpecifications;
    private final AnneeRepository anneeRepository;
    private final UserRoleRepository userRoleRepository;
    @Autowired
    public EnseignantService(EnseignantRepository enseignantRepository,
                             AffectationRepository affectationRepository,
                             UserRepository userRepository,
                             UserSpecification userSpecifications,
                             EnseignantSpecification enseignantSpecifications,
                             AnneeRepository anneeRepository,
                             UserRoleRepository userRoleRepository) {
        this.enseignantRepository = enseignantRepository;
        this.affectationRepository = affectationRepository;
        this.userRepository = userRepository;
        this.userSpecifications = userSpecifications;
        this.enseignantSpecifications = enseignantSpecifications;
        this.anneeRepository = anneeRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public List<Affectation> getAffectationsByEnseignantById(Long userId) {

        // Get the enseignant id from the user id


        Enseignant enseignant = enseignantRepository.findByUserId(userId)
                .orElseThrow(NotFoundException::new);

        return enseignant.getAffectations();
    }

    public List<AffectationDTO> getAffectationsByEnseignantIdFormated(Long id) {
        List<Affectation> affectations = getAffectationsByEnseignantById(id);
        return affectations.stream()
                .map(affectation -> new AffectationDTO(
                        affectation.getId(),
                        affectation.getHeuresAssignees(),
                        affectation.getDateAffectation(),
                        affectation.getGroupe() != null && affectation.getGroupe().getModule() != null ? affectation.getGroupe().getModule().getNom() : null,
                        affectation.getCommentaire(),
                        affectation.getGroupe() != null ? affectation.getGroupe().getNom() : null
                ))
                .collect(Collectors.toList());
    }


    public Enseignant getEnseignant() {
        return enseignantRepository.findAll().get(0);
    }

    public List<User> getUsersWithRoleEnseignantNotInEnseignant() {
        //List<User> users = this.userRepository.findUsersByRolesNotLike(Role.ENSEIGNANT);
        Specification<User> spec = userSpecifications.notInEnseignant();
        //users.addAll(this.userRepository.findAll(spec));
        //return users;
        return this.userRepository.findAll(spec);
    }

    public List<Enseignant> getEnseignants(){
        //Specification<User> spec = userSpecifications.withRoleEnseignant();
        return this.enseignantRepository.findAll();
    }

    public Enseignant createEnseignant(User u, int nmaxHeuresService, int heuresAssignees,
                                       CategorieEnseignant categorieEnseignant, int nbHeureCategorie, Long currentYear) {

        // Find the user by ID
        User user = userRepository.findById(u.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + u.getId()));

        // If the current year doesn't have the role of 'ENSEIGNANT', add it
        if(!user.hasRoleForYear(currentYear, Role.ENSEIGNANT)){
            user.addRole(Annee.builder().id(currentYear).build(), Role.ENSEIGNANT);
            // Save the updated user with the new role
            user = userRepository.save(user);
        }

        // Create a map for the categories and hours
        Map<CategorieEnseignant, Integer> categorieHeuresMap = new HashMap<>();
        categorieHeuresMap.put(categorieEnseignant, nbHeureCategorie);
        // Create the Enseignant entity
        Enseignant enseignant = Enseignant.builder()
                .categorieEnseignant(categorieHeuresMap)
                .user(user)
                .name(user.getName())
                .firstname(user.getFirstname())
                .hasAccount(true)
                .maxHeuresService(nmaxHeuresService)
                .heuresAssignees(0)
                .build();

        // Save and return the Enseignant entity
        return enseignantRepository.save(enseignant);
    }

    public Enseignant createEnseignantWithoutAccount(String name, String firstname, int nmaxHeuresService, int heuresAssignees,
                                                     CategorieEnseignant categorieEnseignant, int nbHeureCategorie) {

        Map<CategorieEnseignant, Integer> categorieHeuresMap = new HashMap<>();
        categorieHeuresMap.put(categorieEnseignant, nbHeureCategorie);

        Enseignant enseignant = Enseignant.builder()
                .hasAccount(false)
                .maxHeuresService(nmaxHeuresService)
                .heuresAssignees(0)
                .firstname(StringUtils.capitalize(firstname))
                .name(StringUtils.capitalize(name))
                .categorieEnseignant(categorieHeuresMap)
                .build();

        return enseignantRepository.save(enseignant);
    }


    @Transactional
    public Enseignant updateEnseignant(long id, int nmaxHeuresService, CategorieEnseignant categorieEnseignant, int nbHeureCategorie,
                                       String name, String firstname, boolean hasAccount) {

        // Mise à jour des données de l'enseignant
        Map<CategorieEnseignant, Integer> categorieHeuresMap = new HashMap<>();
        categorieHeuresMap.put(categorieEnseignant, nbHeureCategorie);

        Enseignant enseignant = this.enseignantRepository.getReferenceById(id);

        enseignant.setCategorieEnseignant(categorieHeuresMap);
        enseignant.setMaxHeuresService(nmaxHeuresService);
        enseignant.setName(StringUtils.capitalize(name));
        enseignant.setFirstname(StringUtils.capitalize(firstname));
        enseignant.setHasAccount(hasAccount);

        if (!hasAccount) {
            if (enseignant.getUser() != null) {
                // Récupérer l'utilisateur associé
                User user = userRepository.findById(enseignant.getUser().getId())
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + enseignant.getUser().getId()));

                // Récupération des rôles existants pour l'utilisateur et l'année courante
                List<UserRole> existingRoles = this.userRoleRepository.findByUserIdAndYearId(
                        user.getId(),
                        this.anneeRepository.getCurrentYear()
                                .orElseThrow(() -> new RuntimeException("Current year not found"))
                                .getId()
                );

                // Supprimer les rôles ENSEIGNANT côté UserRole
                List<UserRole> rolesToDelete = existingRoles.stream()
                        .filter(role -> role.getRole() == Role.ENSEIGNANT)
                        .toList();
                rolesToDelete.forEach(userRole ->
                        this.userRoleRepository.deleteByUserIdAndYearId(userRole.getUser().getId(),
                                userRole.getYear().getId())
                );

                // Mettre à jour les rôles côté utilisateur, en excluant ENSEIGNANT
                List<UserRole> updatedRoles = existingRoles.stream()
                        .filter(role -> role.getRole() != Role.ENSEIGNANT)
                        .collect(Collectors.toList());

                user.setRoles(updatedRoles);

                // Sauvegarde des modifications pour l'utilisateur
                this.userRepository.save(user);
            }
            enseignant.setUser(null);
        }
        return this.enseignantRepository.save(enseignant);
    }

    public  Enseignant updateEnseignant(long id, int nmaxHeuresService, CategorieEnseignant categorieEnseignant, int nbHeureCategorie, User user, boolean hasAccount ) {

        User finalUser = user;
        user = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + finalUser.getId()));

        if(!user.hasRoleForYear(this.anneeRepository.getCurrentYear().get().getId(), Role.ENSEIGNANT)){
            user.addRole(this.anneeRepository.getCurrentYear().get(), Role.ENSEIGNANT);
            user = userRepository.save(user);
        }

        Map<CategorieEnseignant, Integer> categorieHeuresMap = new HashMap<>();
        categorieHeuresMap.put(categorieEnseignant, nbHeureCategorie);
        Enseignant enseignant = this.enseignantRepository.getReferenceById(id);
        enseignant.setCategorieEnseignant(categorieHeuresMap);
        enseignant.setMaxHeuresService(nmaxHeuresService);
        enseignant.setUser(user);
        enseignant.setHasAccount(hasAccount);
        return this.enseignantRepository.save(enseignant);
    }

    public Enseignant getEnseignantById(Long id) {
        return this.enseignantRepository.getReferenceById(id);
    }


    public void updateCommentaireAffectation(Long affectationId, String connectedUserName, String commentaire){

        User user = userRepository.findOneUserByUsername(connectedUserName).orElseThrow(UnauthorizedException::new);
        Enseignant enseignant = enseignantRepository.findByUserId(user.getId()).orElseThrow(UnauthorizedException::new);

        Affectation affectation = affectationRepository.findByEnseignantIdAndAssignationId(enseignant.getId(), affectationId)
                .orElseThrow(NotFoundException::new);

        affectation.setCommentaire(commentaire);
        affectationRepository.save(affectation);

    }
    public Enseignant getEnseignantByUser(Long userId) {
        Specification<Enseignant> spec = enseignantSpecifications.getEnseignantWithUserId(userId);
        return this.enseignantRepository.findOne(spec).orElse(null);
    }

    public List<Enseignant> getEnseignantsWithSameUserNameAndFirstName(String name, String firstname) {
        Specification<Enseignant> spec = this.enseignantSpecifications.byNameandFirstname(name, firstname);
        return this.enseignantRepository.findAll(spec);
    }

    public Optional<Enseignant> getEnseignantByFirstname(String firstname) {
        return enseignantRepository.findByFirstname(firstname);
    }


    public Optional<Enseignant> getEnseignantByName(String name) {
        return enseignantRepository.findByName(name);
    }

}
