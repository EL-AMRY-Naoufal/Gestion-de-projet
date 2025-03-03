package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.ResponsableFormation;
import com.fst.il.m2.Projet.repositories.ResponsableFormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponsableFormationServiceDefault implements ResponsableFormationService{
    @Autowired
    ResponsableFormationRepository responsableFormationRepository;
    @Override
    public ResponsableFormation getResponsableFormationById(Long id) {
        return responsableFormationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Responsable de Formation not found"));
    }
}
