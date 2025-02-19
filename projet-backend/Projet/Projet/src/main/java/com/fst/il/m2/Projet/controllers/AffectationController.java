package com.fst.il.m2.Projet.controllers;


import com.fst.il.m2.Projet.business.AffectationService;
import com.fst.il.m2.Projet.business.ResponsableDepartementService;
import com.fst.il.m2.Projet.models.Affectation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/affectation")

public class AffectationController {

    @Autowired
    private AffectationService affectationService;

    // Affecter un enseignant à un module avec un nombre d'heures
    @PostMapping("/{idEnseignant}/{idGroupe}/{heure}")
    public ResponseEntity<Affectation> affecterEnseignant(
            @PathVariable Long idEnseignant,
            @PathVariable Long idGroupe,
            @PathVariable int heure
    ) {
        Affectation affectation = affectationService.affecterModuleToEnseignant(idEnseignant, idGroupe, heure);
        return ResponseEntity.ok(affectation);
    }

    // Mettre à jour le nombre d'heures enseignées pour une affectation existante
    @PutMapping("/{idAffectation}/{heure}")
    public ResponseEntity<String> updateAffectation(
            @PathVariable Long idAffectation,
            @PathVariable int heure
    ) {
        affectationService.updateAffectationHours(idAffectation, heure);
        return ResponseEntity.ok("Affectation mise à jour avec succès");
    }

    // Supprimer une affectation
    @DeleteMapping("/{idAffectation}")
    public ResponseEntity<String> deleteAffectation(@PathVariable Long idAffectation) {
        affectationService.deleteAffectation(idAffectation);
        return ResponseEntity.noContent().build();
    }
}
