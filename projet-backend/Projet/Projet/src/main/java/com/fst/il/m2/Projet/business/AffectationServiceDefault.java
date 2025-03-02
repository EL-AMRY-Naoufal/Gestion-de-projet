package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.dto.CoAffectationDTO;
import com.fst.il.m2.Projet.exceptions.NotFoundException;
import com.fst.il.m2.Projet.mapper.AffectationMapper;
import com.fst.il.m2.Projet.models.Affectation;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.Groupe;
import com.fst.il.m2.Projet.repositories.AffectationRepository;
import com.fst.il.m2.Projet.repositories.EnseignantRepository;
import com.fst.il.m2.Projet.repositories.GroupeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AffectationServiceDefault implements AffectationService{


    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private AffectationRepository affectationRepository;

    @Override
    public List<Affectation> getAllAffectations() {
        return affectationRepository.findAll();
    }


    @Override
    @Transactional
    public Affectation saveAffectation(Affectation affectation) {
        return affecterGroupeToEnseignant(affectation.getEnseignant().getId(), affectation.getGroupe().getId(), affectation.getHeuresAssignees());
    }

    @Override
    public  Affectation affecterGroupeToEnseignant(Long userId, Long groupeId, int heuresAssignees) {

        /*if(heuresAssignees <= 0){
            throw new RuntimeException("Heures assignées must be greater than 0");
        }*/

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
    public List<Affectation> getAffectationsByGroupe(Groupe groupe) {
        return affectationRepository.findGroupeAffectations(groupe);
    }


    @Override
    public Affectation getAffectationById(Long id) {
        return affectationRepository.findById(id).orElseThrow(() -> new RuntimeException("Affectation not found"));
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

    @Override
    public List<CoAffectationDTO> getCoAffectationsByModuleId(Long moduleId) {
        List<Affectation> affectations = affectationRepository.findByModuleId(moduleId);
        
        return affectations.stream()
                .map(AffectationMapper::toCoAffectationDTO)
                .collect(Collectors.toList());
    }
}
