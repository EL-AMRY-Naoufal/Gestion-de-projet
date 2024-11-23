package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
import com.fst.il.m2.Projet.dto.AffectationDTO;
import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.Affectation;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.repositories.AffectationRepository;
import com.fst.il.m2.Projet.repositories.EnseignantRepository;
import com.fst.il.m2.Projet.repositories.UserRepository;
import com.fst.il.m2.Projet.repositories.specifications.EnseignantSpecification;
import com.fst.il.m2.Projet.repositories.specifications.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final AffectationRepository affectationRepository;
    private final UserRepository userRepository;
    private final UserSpecification userSpecifications;
    private final EnseignantSpecification enseignantSpecifications;

    public List<Affectation> getAffectationsByEnseignantById(Long enseignantId) {
        Enseignant enseignant = enseignantRepository.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant not found"));
        return enseignant.getAffectations();
    }


    public List<AffectationDTO> getAffectationsByEnseignantIdFormated(Long id) {
        List<Affectation> affectations = getAffectationsByEnseignantById(id);
        return affectations.stream()
                .map(affectation -> new AffectationDTO(
                        affectation.getId(),
                        affectation.getHeuresAssignees(),
                        affectation.getDateAffectation(),
                        affectation.getModule() != null ? affectation.getModule().getNom() : null
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

    public List<User> getEnseignants(){
        Specification<User> spec = userSpecifications.withRoleEnseignant();
        return this.userRepository.findAll(spec);
    }

    public Enseignant createEnseignant(long id, int nmaxHeuresService, int heuresAssignees,  CategorieEnseignant categorieEnseignant, int nbHeureCategorie) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        List<Role>  roles = user.getRoles();
        if(!roles.contains(Role.ENSEIGNANT)) {
            roles.add(Role.ENSEIGNANT);
        }
        user = this.userRepository.save(user);
        Map<CategorieEnseignant, Integer> categorieHeuresMap = new HashMap<>();
        categorieHeuresMap.put(categorieEnseignant, nbHeureCategorie);
        Enseignant enseignant = Enseignant.builder()
                .categorieEnseignant(categorieHeuresMap)
                .user(user)
                .maxHeuresService(nmaxHeuresService).heuresAssignees(heuresAssignees)
                .build();

        return this.enseignantRepository.save(enseignant);
    }

    public  Enseignant updateEnseignant(long id, int nmaxHeuresService, CategorieEnseignant categorieEnseignant, int nbHeureCategorie ) {
        Map<CategorieEnseignant, Integer> categorieHeuresMap = new HashMap<>();
        categorieHeuresMap.put(categorieEnseignant, nbHeureCategorie);
        Enseignant enseignant = this.enseignantRepository.getReferenceById(id);
        enseignant.setCategorieEnseignant(categorieHeuresMap);
        enseignant.setMaxHeuresService(nmaxHeuresService);
        return this.enseignantRepository.save(enseignant);
    }

    public Enseignant getEnseignantById(Long id) {
        Specification<Enseignant> spec = enseignantSpecifications.getEnseignantWithUserId(id);
        return this.enseignantRepository.findOne(spec).orElseThrow(() -> new RuntimeException("Enseignant not found with id: " + id));
    }

}