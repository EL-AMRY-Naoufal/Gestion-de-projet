package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.dto.CoAffectationDTO;
import com.fst.il.m2.Projet.exceptions.NotFoundException;
import com.fst.il.m2.Projet.mapper.AffectationMapper;
import com.fst.il.m2.Projet.models.*;
import com.fst.il.m2.Projet.repositories.*;
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
    @Autowired
    private AnneeRepository anneeRepository;
    @Autowired
    private HeuresAssigneesRepository heuresAssigneesRepository;

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
    public  Affectation affecterGroupeToEnseignant(Long userId, Long groupeId, double heuresAssignees) {

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
        groupe.setHeuresAffectees(groupe.getHeuresAffectees() + (int) heuresAssignees);
        groupeRepository.save(groupe);

        // Créer une nouvelle affectation
        Affectation affectation = new Affectation();
        affectation.setEnseignant(enseignant);
        affectation.setGroupe(groupe);
        affectation.setHeuresAssignees((int)heuresAssignees);
        affectation.setDateAffectation(LocalDate.now());
        affectation.setCommentaire("");

        // Sauvegarder l'affectation
        affectationRepository.save(affectation);

        heuresAssignees = heuresAssignees * groupe.getType().getCoef();

        Annee anneeActuelle = anneeRepository.getCurrentYear().get();

        HeuresAssignees heuresAnnee = enseignant.getHeuresParAnnee().stream()
                .filter(h -> h.getAnnee() == anneeActuelle)
                .findFirst()
                .orElseGet(() -> {
                    HeuresAssignees newHeures = new HeuresAssignees();
                    newHeures.setAnnee(anneeActuelle);
                    newHeures.setHeures(0);
                    newHeures.setEnseignant(enseignant);
                    enseignant.getHeuresParAnnee().add(newHeures);
                    return newHeures;
                });
        // Mettre à jour les heures assignées de l'enseignant
        heuresAnnee.setHeures(heuresAnnee.getHeures() + heuresAssignees);
        enseignantRepository.save(enseignant);

        return affectation;
    }


    //mise a jour des heures enseignées d'une affectation
    public void updateAffectationHours(Long idAffectation, double heuresAssignees) {
        if(heuresAssignees <= 0){
            throw new RuntimeException("Heures assignées must be greater than 0");
        }
        Affectation affectation = affectationRepository.findById(idAffectation)
                .orElseThrow(NotFoundException::new);


        // Mis à jour des heures assignées de l'enseignant
        Enseignant enseignant = affectation.getEnseignant();
        double coef =  affectation.getGroupe().getType().getCoef();
        double nbHeureDeleted = affectation.getHeuresAssignees() * coef;
        double nbHeureAdded = heuresAssignees * coef;

        Annee anneeActuelle = anneeRepository.getCurrentYear().get();

        HeuresAssignees heuresAnnee = enseignant.getHeuresParAnnee().stream()
                .filter(h -> h.getAnnee() == anneeActuelle)
                .findFirst()
                .orElseGet(() -> {
                    HeuresAssignees newHeures = new HeuresAssignees();
                    newHeures.setAnnee(anneeActuelle);
                    newHeures.setHeures(0);
                    newHeures.setEnseignant(enseignant);
                    enseignant.getHeuresParAnnee().add(newHeures);
                    return newHeures;
                });

        heuresAnnee.setHeures(heuresAnnee.getHeures() - nbHeureDeleted + nbHeureAdded);
        enseignantRepository.save(enseignant);

        // Mis à jour des heures assignées du groupe
        Groupe groupe = affectation.getGroupe();
        groupe.setHeuresAffectees(groupe.getHeuresAffectees() - affectation.getHeuresAssignees() + (int) heuresAssignees);

        // Mis à jour des heures assignées de l'affectation
        affectation.setHeuresAssignees((int)heuresAssignees);
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
        double nbHeureDeleted = affectation.getHeuresAssignees() *
                affectation.getGroupe().getType().getCoef();

        Annee anneeActuelle = anneeRepository.getCurrentYear().get();
        HeuresAssignees heuresAnnee = enseignant.getHeuresParAnnee().stream()
                .filter(h -> h.getAnnee() == anneeActuelle)
                .findFirst()
                .orElse(null);
        if (heuresAnnee != null) {
            // Mise à jour des heures assignées de l'enseignant
            heuresAnnee.setHeures(heuresAnnee.getHeures() - nbHeureDeleted);
            heuresAssigneesRepository.save(heuresAnnee);
        }

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
