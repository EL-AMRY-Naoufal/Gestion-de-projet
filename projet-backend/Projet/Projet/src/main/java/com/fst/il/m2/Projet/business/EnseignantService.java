package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Affectation;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.repositories.AffectationRepository;
import com.fst.il.m2.Projet.repositories.EnseignantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final AffectationRepository affectationRepository;

    @Autowired
    public EnseignantService(EnseignantRepository enseignantRepository, AffectationRepository affectationRepository) {
        this.enseignantRepository = enseignantRepository;
        this.affectationRepository = affectationRepository;
    }

    public List<Affectation> getAffectationsByEnseignantId(Long enseignantId) {
        Enseignant enseignant = enseignantRepository.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant not found"));
        return enseignant.getAffectations();
    }


    public Enseignant getEnseignant() {
        return enseignantRepository.findAll().get(0);
    }
}
