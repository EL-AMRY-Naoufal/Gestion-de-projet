package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Affectation;
import com.fst.il.m2.Projet.repositories.AffectationRepository;
import com.fst.il.m2.Projet.repositories.AnneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AffectationServiceDefault implements AffectationService{
    @Autowired
    private AffectationRepository affectationRepository;


    @Override
    public Affectation saveAffectation(Affectation affectation) {
        return affectationRepository.save(affectation);
    }

    @Override
    public List<Affectation> getAllAffectations() {
        return affectationRepository.findAll();
    }

    @Override
    public Affectation getAffectationById(Long id) {
        return affectationRepository.findById(id).orElseThrow(() -> new RuntimeException("Affectation not found"));
    }

    @Override
    public void deleteAffectation(Long id) {
        affectationRepository.deleteById(id);
    }
}
