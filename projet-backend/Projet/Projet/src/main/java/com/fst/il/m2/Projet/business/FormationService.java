package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Departement;
import com.fst.il.m2.Projet.models.Formation;

import java.util.List;

public interface FormationService {
    List<Formation> getAllFormations();
    Formation getFormationById(Long id);
    Formation saveFormation(Formation formation);
    Formation updateFormation(Long id, Formation formation);
    List<Formation> getFormationsByDepartement(Departement departement);
    Boolean hasNiveaux(Long id);
    void deleteFormation(Long id);
}
