package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Formation;

import java.util.List;

public interface FormationService {
    List<Formation> getAllFormations();
    Formation getFormationById(Long id);
    Formation saveFormation(Formation formation);
    Formation updateFormation(Long id, Formation formation);
    void deleteFormation(Long id);
}
