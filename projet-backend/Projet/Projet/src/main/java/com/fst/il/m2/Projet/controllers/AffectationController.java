package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.AffectationService;
import com.fst.il.m2.Projet.dto.AffectationDto;
import com.fst.il.m2.Projet.mapper.AffectationMapper;
import com.fst.il.m2.Projet.business.EnseignantService;
import com.fst.il.m2.Projet.dto.CommentaireDto;
import com.fst.il.m2.Projet.business.EnseignantService;
import com.fst.il.m2.Projet.dto.CommentaireDto;
import com.fst.il.m2.Projet.mapper.AffectationMapper;
import com.fst.il.m2.Projet.models.Affectation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/affectations")

public class AffectationController {

    private final AffectationService affectationService;

    private final EnseignantService enseignantService;

    @Autowired
    public AffectationController(AffectationService affectationService, EnseignantService enseignantService) {
        this.affectationService = affectationService;
        this.enseignantService = enseignantService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<List<AffectationDto>> getAffectationsByEnseignantId(@PathVariable Long id) {

        List<AffectationDto> affectations = enseignantService.getAffectationsByEnseignantIdFormated(id);

        if (affectations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(affectations, HttpStatus.OK);
    }

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

    @PostMapping
    public ResponseEntity<AffectationDto> saveAffectation(@RequestBody AffectationDto affectationDto) {
        Affectation savedAffectation = affectationService.saveAffectation(AffectationMapper.toEntity(affectationDto));
        return new ResponseEntity<>(AffectationMapper.toDto(savedAffectation), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AffectationDto>> getAllAffectations() {
        List<Affectation> affectations = affectationService.getAllAffectations();
        return new ResponseEntity<>(affectations.stream().map(AffectationMapper::toDto).toList(), HttpStatus.OK);
    }

    @PutMapping("/{id}/{idAffectation}/commentaire")
    public CommentaireDto updateCommentaireAffectation(@PathVariable Long idAffectation, @RequestBody CommentaireDto commentaireDto, @CurrentSecurityContext(expression = "authentication?.name") String username){
        enseignantService.updateCommentaireAffectation(idAffectation, username, commentaireDto.getCommentaire());
        return CommentaireDto.builder().commentaire(commentaireDto.getCommentaire()).build();
    }

}
