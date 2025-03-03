package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.Role;
import org.springframework.stereotype.Service;

import com.fst.il.m2.Projet.exceptions.NotFoundException;
import com.fst.il.m2.Projet.models.*;
import com.fst.il.m2.Projet.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AffectationServiceDefault implements AffectationService{


    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private AffectationRepository affectationRepository;

    @Override
    @Transactional
    public  Affectation affecterModuleToEnseignant(Long userId, Long groupeId, int heuresAssignees) {

        if(heuresAssignees <= 0){
            throw new RuntimeException("Heures assignées must be greater than 0");
        }

        Enseignant enseignant = enseignantRepository.findById(userId)
                .orElseThrow(NotFoundException::new);

        // Récupérer le groupe
        Groupe groupe = groupeRepository.findById(groupeId)
                .orElseThrow(NotFoundException::new);

        // Vérifier si l'enseignant est déjà affecté à ce groupe
        if (affectationRepository.existsByEnseignantAndGroupe(enseignant, groupe)) {
            throw new RuntimeException("Affectation already exists");
        }

        // Mettre à jour les heures restantes du groupe
        groupe.setHeuresAffectees(groupe.getHeuresAffectees() + heuresAssignees);
        groupeRepository.save(groupe);

        // Créer une nouvelle affectation
        Affectation affectation = new Affectation();
        affectation.setEnseignant(enseignant);
        affectation.setGroupe(groupe);
        affectation.setHeuresAssignees(heuresAssignees);
        affectation.setDateAffectation(LocalDate.now());
        affectation.setCommentaire("");

        // Sauvegarder l'affectation
        affectationRepository.save(affectation);

        // Mettre à jour les heures assignées de l'enseignant
        enseignant.setHeuresAssignees(enseignant.getHeuresAssignees() + heuresAssignees);
        enseignantRepository.save(enseignant);

        return affectation;
    }


    //mise a jour des heures enseignées d'une affectation
    public void updateAffectationHours(Long idAffectation, int heuresAssignees) {
        if(heuresAssignees <= 0){
            throw new RuntimeException("Heures assignées must be greater than 0");
        }

        Affectation affectation = affectationRepository.findById(idAffectation)
                .orElseThrow(NotFoundException::new);


        // Mis à jour des heures assignées de l'enseignant
        Enseignant enseignant = affectation.getEnseignant();
        enseignant.setHeuresAssignees(enseignant.getHeuresAssignees() - affectation.getHeuresAssignees() + heuresAssignees);
        enseignantRepository.save(enseignant);

        // Mis à jour des heures assignées du groupe
        Groupe groupe = affectation.getGroupe();
        groupe.setHeuresAffectees(groupe.getHeuresAffectees() - affectation.getHeuresAssignees() + heuresAssignees);

        // Mis à jour des heures assignées de l'affectation
        affectation.setHeuresAssignees(heuresAssignees);
        affectationRepository.save(affectation);
    }



    @Override
    public void deleteAffectation(Long id){
        Affectation affectation = affectationRepository.findById(id)
                .orElseThrow(NotFoundException::new);


        Enseignant enseignant = affectation.getEnseignant();
        enseignant.setHeuresAssignees(enseignant.getHeuresAssignees() - affectation.getHeuresAssignees());

        Groupe groupe = affectation.getGroupe();
        groupe.setHeuresAffectees(groupe.getHeuresAffectees() - affectation.getHeuresAssignees());
        groupeRepository.save(groupe);
        enseignantRepository.save(enseignant);
        affectationRepository.deleteById(id);
    }


}
