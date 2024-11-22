package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.repositories.EnseignantRepository;
import com.fst.il.m2.Projet.repositories.UserRepository;
import com.fst.il.m2.Projet.repositories.specifications.UserSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnseignantService {
    private final EnseignantRepository enseignantRepository;
    private final UserRepository userRepository;
    private final UserSpecifications userSpecifications;
    private final AffectationRepository affectationRepository;

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
        Specification<User> spec = userSpecifications.withRoleEnseignantAndNotInEnseignant();
        return this.userRepository.findAll(spec);
    }

    public Enseignant createEnseignant(long id, int nmaxHeuresService, int heuresAssignees, CategorieEnseignant categorieEnseignant) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        Enseignant enseignant = Enseignant.builder().categorie(categorieEnseignant)
                .maxHeuresService(nmaxHeuresService).heuresAssignees(heuresAssignees)
                .user(user)
                .build();
        return this.enseignantRepository.save(enseignant);
    }

    public  Enseignant updateEnseignant(long id, int nmaxHeuresService, CategorieEnseignant categorieEnseignant ) {
        Enseignant enseignant = this.enseignantRepository.getReferenceById(id);
        enseignant.setCategorie(categorieEnseignant);
        enseignant.setMaxHeuresService(nmaxHeuresService);
        return this.enseignantRepository.save(enseignant);
    }

    public Enseignant getEnseignantById(long id) {
        return this.enseignantRepository.getReferenceById(id);
    }

}
