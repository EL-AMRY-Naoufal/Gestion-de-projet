package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.dto.EnseignantDto;
import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
import com.fst.il.m2.Projet.dto.AffectationDTO;
import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final AffectationRepository affectationRepository;
    private final UserRepository userRepository;
    private final UserSpecification userSpecifications;
    private final EnseignantSpecification enseignantSpecifications;
    private final AnneeRepository anneeRepository;

    @Autowired
    public EnseignantService(EnseignantRepository enseignantRepository,
                             AffectationRepository affectationRepository,
                             UserRepository userRepository,
                             UserSpecification userSpecifications,
                             EnseignantSpecification enseignantSpecifications,
                             AnneeRepository anneeRepository) {
        this.enseignantRepository = enseignantRepository;
        this.affectationRepository = affectationRepository;
        this.userRepository = userRepository;
        this.userSpecifications = userSpecifications;
        this.enseignantSpecifications = enseignantSpecifications;
        this.anneeRepository = anneeRepository;
    }

    public List<Affectation> getAffectationsByEnseignantById(Long userId) {

        //affiche l'id de l'enseignant demandé
        //System.err.println("userId: " + userId);

      /* //afficvhe tous les enseignants
        List<Enseignant> enseignants = enseignantRepository.findAll();
        for (Enseignant enseignant : enseignants) {
            System.err.println("enseignant: " + enseignant);
        }
*/
        // Get the enseignant id from the user id

        System.out.println(userId);

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
                        affectation.getGroupe() != null ? affectation.getGroupe().getNom() : null,
                        affectation.getCommentaire()
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
        heuresAssignees += nbHeureCategorie;
        // Create the Enseignant entity
        Enseignant enseignant = Enseignant.builder()
                .categorieEnseignant(categorieHeuresMap)
                .user(user)
                .name(user.getName())
                .firstname(user.getFirstname())
                .hasAccount(true)
                .maxHeuresService(nmaxHeuresService)
                .heuresAssignees(heuresAssignees)
                .build();

        // Save and return the Enseignant entity
        return enseignantRepository.save(enseignant);
    }

    public Enseignant createEnseignantWithoutAccount(String name, String firstname, int nmaxHeuresService, int heuresAssignees,
                                                     CategorieEnseignant categorieEnseignant, int nbHeureCategorie) {

        Map<CategorieEnseignant, Integer> categorieHeuresMap = new HashMap<>();
        categorieHeuresMap.put(categorieEnseignant, nbHeureCategorie);

        heuresAssignees += nbHeureCategorie;

        Enseignant enseignant = Enseignant.builder()
                .hasAccount(false)
                .maxHeuresService(nmaxHeuresService)
                .heuresAssignees(heuresAssignees)
                .firstname(firstname)
                .name(name)
                .categorieEnseignant(categorieHeuresMap)
                .build();

        return enseignantRepository.save(enseignant);
    }


    public  Enseignant updateEnseignant(long id, int nmaxHeuresService, CategorieEnseignant categorieEnseignant, int nbHeureCategorie,
                                        String name, String firstname, boolean hasAccount) {


        Map<CategorieEnseignant, Integer> categorieHeuresMap = new HashMap<>();
        categorieHeuresMap.put(categorieEnseignant, nbHeureCategorie);
        Enseignant enseignant = this.enseignantRepository.getReferenceById(id);
        CategorieEnseignant categorie = enseignant.getCategorieEnseignant().keySet().stream().findFirst().orElse(CategorieEnseignant.ENSEIGNANT_CHERCHEUR);
        nbHeureCategorie -= enseignant.getNbHeureCategorie(categorie);
        enseignant.setCategorieEnseignant(categorieHeuresMap);
        enseignant.setMaxHeuresService(nmaxHeuresService);
        enseignant.setName(name);
        enseignant.setFirstname(firstname);
        enseignant.setHeuresAssignees(enseignant.getHeuresAssignees() + nbHeureCategorie);
        enseignant.setHasAccount(hasAccount);

        if(!hasAccount){
            if(enseignant.getUser() != null){
                User user = userRepository.findById(enseignant.getUser().getId())
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + enseignant.getUser().getId()));

                user.getRoles().forEach(role -> {
                    System.out.println("Role: " + role.getRole());
                    System.out.println("Year: " + role.getYear());
                });
                System.out.println("Current Year: " + anneeRepository.getCurrentYear().get().getId());

                List<UserRole> roles = user.getRoles().stream().filter(userRole ->
                        Objects.equals(userRole.getYear(), this.anneeRepository.getCurrentYear().get().getId()) &&
                        userRole.getRole() == Role.ENSEIGNANT).toList();

                for (UserRole role : roles) {
                    System.out.println("Role: 1");
                    user.getRoles().remove(role);
                }
                if(!user.getRoles().isEmpty()){
                    System.out.println("Current Year: " + anneeRepository.getCurrentYear().get().getId());
                }
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

        // If the current year doesn't have the role of 'ENSEIGNANT', add it
        if(!user.hasRoleForYear(this.anneeRepository.getCurrentYear().get().getId(), Role.ENSEIGNANT)){
            user.addRole(this.anneeRepository.getCurrentYear().get().getId(), Role.ENSEIGNANT);
            // Save the updated user with the new role
            user = userRepository.save(user);
        }

        Map<CategorieEnseignant, Integer> categorieHeuresMap = new HashMap<>();
        categorieHeuresMap.put(categorieEnseignant, nbHeureCategorie);
        Enseignant enseignant = this.enseignantRepository.getReferenceById(id);
        CategorieEnseignant categorie = enseignant.getCategorieEnseignant().keySet().stream().findFirst().orElse(CategorieEnseignant.ENSEIGNANT_CHERCHEUR);
        nbHeureCategorie -= enseignant.getNbHeureCategorie(categorie);
        enseignant.setCategorieEnseignant(categorieHeuresMap);
        enseignant.setMaxHeuresService(nmaxHeuresService);
        enseignant.setHeuresAssignees(enseignant.getHeuresAssignees() + nbHeureCategorie);
        enseignant.setUser(user);
        enseignant.setHasAccount(hasAccount);
        return this.enseignantRepository.save(enseignant);
    }

    public Enseignant getEnseignantById(Long id) {
        //Specification<Enseignant> spec = enseignantSpecifications.getEnseignantWithUserId(id);
        return this.enseignantRepository.getReferenceById(id);
    }


    public String updateCommentaireAffectation(Long affectationId, String connectedUserName, String commentaire){

        User user = userRepository.findOneUserByUsername(connectedUserName).orElseThrow(UnauthorizedException::new);
        Enseignant enseignant = enseignantRepository.findByUserId(user.getId()).orElseThrow(UnauthorizedException::new);

        Affectation affectation = affectationRepository.findByEnseignantIdAndAssignationId(enseignant.getId(), affectationId)
                .orElseThrow(NotFoundException::new);

        affectation.setCommentaire(commentaire);
        affectationRepository.save(affectation);

        return commentaire;
    }

}
