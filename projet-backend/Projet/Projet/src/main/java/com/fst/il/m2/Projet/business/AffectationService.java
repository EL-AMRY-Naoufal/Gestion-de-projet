package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Affectation;
import com.fst.il.m2.Projet.models.Groupe;

import java.util.List;

public interface AffectationService {
    Affectation saveAffectation(Affectation affectation);

    List<Affectation> getAllAffectations();

    Affectation getAffectationById(Long id);

    Affectation affecterGroupeToEnseignant(Long userId, Long groupeId, int heuresAssignees);

    void updateAffectationHours(Long idAffectation, int heuresAssignees);

    List<Affectation> getAffectationsByGroupe(Groupe groupe);

    void deleteAffectation(Long id);
}
