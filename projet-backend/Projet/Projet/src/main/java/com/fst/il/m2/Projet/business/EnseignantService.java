package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.dto.AffectationDTO;
import com.fst.il.m2.Projet.models.Affectation;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.repositories.AffectationRepository;
import com.fst.il.m2.Projet.repositories.EnseignantRepository;
import com.fst.il.m2.Projet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final AffectationRepository affectationRepository;
    private final UserRepository userRepository;

    @Autowired
    public EnseignantService(EnseignantRepository enseignantRepository, AffectationRepository affectationRepository, UserRepository userRepository) {
        this.enseignantRepository = enseignantRepository;
        this.affectationRepository = affectationRepository;
        this.userRepository = userRepository;
    }

    public List<Affectation> getAffectationsByEnseignantById(Long enseignantId) {
        User UserRepository = userRepository.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant not found"));
        return enseignantRepository.getAffectations();
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
}