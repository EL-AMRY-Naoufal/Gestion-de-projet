package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.AffectationService;
import com.fst.il.m2.Projet.dto.AffectationDto;
import com.fst.il.m2.Projet.dto.CoAffectationDTO;
import com.fst.il.m2.Projet.mapper.AffectationMapper;
import com.fst.il.m2.Projet.business.EnseignantService;
import com.fst.il.m2.Projet.dto.CommentaireDto;
import com.fst.il.m2.Projet.business.EnseignantService;
import com.fst.il.m2.Projet.dto.CommentaireDto;
import com.fst.il.m2.Projet.mapper.AffectationMapper;
import com.fst.il.m2.Projet.models.Affectation;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.repositories.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public AffectationController(AffectationService affectationService, EnseignantService enseignantService, UserRepository userRepository) {
        this.affectationService = affectationService;
        this.enseignantService = enseignantService;
        this.userRepository = userRepository;
    }

    //get coaffectation by the module id
    @GetMapping("/coAffectations/module/{moduleId}")
    public ResponseEntity<List<CoAffectationDTO>> getCoAffectationsByModuleId(@PathVariable Long moduleId) {
        List<CoAffectationDTO> affectations = affectationService.getCoAffectationsByModuleId(moduleId);
        return new ResponseEntity<>(affectations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<AffectationDto>> getAffectationsByEnseignantId(@PathVariable Long id) {

        List<AffectationDto> affectations = enseignantService.getAffectationsByEnseignantIdFormated(id);

        if (affectations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(affectations, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<AffectationDto> saveAffectation(@RequestBody AffectationDto affectationDto) {
        Affectation savedAffectation = affectationService.saveAffectation(AffectationMapper.toEntity(affectationDto));
        return new ResponseEntity<>(AffectationMapper.toDto(savedAffectation), HttpStatus.CREATED);
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
