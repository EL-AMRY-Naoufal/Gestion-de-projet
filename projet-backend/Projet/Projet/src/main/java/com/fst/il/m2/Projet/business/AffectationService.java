package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Affectation;

public interface AffectationService {

     Affectation affecterModuleToEnseignant(Long userId, Long groupeId, int heuresAssignees);
    void updateAffectationHours(Long idAffectation, int heuresAssignees);
    void deleteAffectation(Long id);
}
