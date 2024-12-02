package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Formation;
import com.fst.il.m2.Projet.repositories.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormationServiceDefault implements FormationService {
    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    public FormationServiceDefault(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    @Override
    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    @Override
    public Formation getFormationById(Long id) {
        return formationRepository.findById(id).orElse(null);
    }

    @Override
    public Formation saveFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    @Override
    public Formation updateFormation(Long id, Formation formation) {
        return formationRepository.findById(id)
                .map(existingFormation -> {
                    existingFormation.setNom(formation.getNom());
                    existingFormation.setTotalHeures(formation.getTotalHeures());
                    existingFormation.setResponsableFormation(formation.getResponsableFormation());
                    existingFormation.setModules(formation.getModules());
                    existingFormation.setNiveaux(formation.getNiveaux());
                    return formationRepository.save(existingFormation);
                })
                .orElseThrow(() -> new RuntimeException("Formation not found with id " + id));
    }

    @Override
    public void deleteFormation(Long id) {
        if (formationRepository.existsById(id)) {
            formationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Formation not found with id " + id);
        }
    }
}
