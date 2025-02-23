package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.dto.AffectationDto;
import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.exceptions.NotFoundException;
import com.fst.il.m2.Projet.exceptions.UnauthorizedException;
import com.fst.il.m2.Projet.mapper.AffectationMapper;
import com.fst.il.m2.Projet.models.Affectation;
import com.fst.il.m2.Projet.models.Annee;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.repositories.AffectationRepository;
import com.fst.il.m2.Projet.repositories.EnseignantRepository;
import com.fst.il.m2.Projet.repositories.UserRepository;
import com.fst.il.m2.Projet.repositories.specifications.EnseignantSpecification;
import com.fst.il.m2.Projet.repositories.specifications.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final AffectationRepository affectationRepository;
    private final UserRepository userRepository;
    private final UserSpecification userSpecifications;
    private final EnseignantSpecification enseignantSpecifications;

    @Autowired
    public EnseignantService(EnseignantRepository enseignantRepository,
                             AffectationRepository affectationRepository,
                             UserRepository userRepository,
                             UserSpecification userSpecifications,
                             EnseignantSpecification enseignantSpecifications) {
        this.enseignantRepository = enseignantRepository;
        this.affectationRepository = affectationRepository;
        this.userRepository = userRepository;
        this.userSpecifications = userSpecifications;
        this.enseignantSpecifications = enseignantSpecifications;
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

    public List<AffectationDto> getAffectationsByEnseignantIdFormated(Long id) {
        List<Affectation> affectations = getAffectationsByEnseignantById(id);
        return affectations.stream().map(AffectationMapper::toDto).toList();
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

    public List<User> getEnseignants(){
        Specification<User> spec = userSpecifications.withRoleEnseignant();
        return this.userRepository.findAll(spec);
    }

    public Enseignant createEnseignant(long id, int nmaxHeuresService, int heuresAssignees,
                                       CategorieEnseignant categorieEnseignant, int nbHeureCategorie, Long currentYear) {

        // Find the user by ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

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
                .maxHeuresService(nmaxHeuresService)
                .heuresAssignees(heuresAssignees)
                .build();

        // Save and return the Enseignant entity
        return enseignantRepository.save(enseignant);
    }


    public Enseignant updateEnseignant(long id, int nmaxHeuresService, CategorieEnseignant categorieEnseignant, int nbHeureCategorie ) {

        Map<CategorieEnseignant, Integer> categorieHeuresMap = new HashMap<>();
        categorieHeuresMap.put(categorieEnseignant, nbHeureCategorie);
        Enseignant enseignant = this.enseignantRepository.getReferenceById(id);
        CategorieEnseignant categorie = enseignant.getCategorieEnseignant().keySet().stream().findFirst().orElse(CategorieEnseignant.ENSEIGNANT_CHERCHEUR);
        nbHeureCategorie -= enseignant.getNbHeureCategorie(categorie);
        enseignant.setCategorieEnseignant(categorieHeuresMap);
        enseignant.setMaxHeuresService(nmaxHeuresService);
        enseignant.setHeuresAssignees(enseignant.getHeuresAssignees() + nbHeureCategorie);
        return this.enseignantRepository.save(enseignant);
    }

    public Enseignant getEnseignantById(Long id) {
        Specification<Enseignant> spec = enseignantSpecifications.getEnseignantWithUserId(id);
        return this.enseignantRepository.findOne(spec).orElseThrow(() -> new RuntimeException("Enseignant not found with id: " + id));
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
