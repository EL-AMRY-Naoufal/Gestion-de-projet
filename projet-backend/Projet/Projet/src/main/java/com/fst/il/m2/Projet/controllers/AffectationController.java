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
import com.fst.il.m2.Projet.models.Annee;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")@RestController
@RequestMapping("/api/affectations")
@Tag(name = "Affectation Controller", description = "Gestion des affectations")
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

    @Operation(summary = "Récupérer les coaffectations par ID de module", description = "Cette méthode permet de récupérer la liste des coaffectations pour un module donné.")
    @GetMapping("/coAffectations/module/{moduleId}")
    public ResponseEntity<List<CoAffectationDTO>> getCoAffectationsByModuleId(@PathVariable Long moduleId) {
        List<CoAffectationDTO> affectations = affectationService.getCoAffectationsByModuleId(moduleId);
        return new ResponseEntity<>(affectations, HttpStatus.OK);
    }

    @Operation(summary = "Récupérer les affectations par ID d'enseignant", description = "Cette méthode permet de récupérer la liste des affectations pour un enseignant donné.")
    @GetMapping("/{id}")
    public ResponseEntity<List<AffectationDto>> getAffectationsByEnseignantId(@PathVariable Long id) {
        List<AffectationDto> affectations = enseignantService.getAffectationsByEnseignantIdFormated(id);
        if (affectations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(affectations, HttpStatus.OK);
    }

    @Operation(summary = "Enregistrer une nouvelle affectation", description = "Cette méthode permet d'enregistrer une nouvelle affectation pour une année donnée.")
    @PostMapping("{annee}")
    public ResponseEntity<AffectationDto> saveAffectation(@RequestBody AffectationDto affectationDto, @PathVariable Long annee) {
        Affectation savedAffectation = affectationService.saveAffectation(AffectationMapper.toEntity(affectationDto), annee);
        return new ResponseEntity<>(AffectationMapper.toDto(savedAffectation), HttpStatus.CREATED);
    }

    @Operation(summary = "Mettre à jour le nombre d'heures enseignées", description = "Cette méthode permet de mettre à jour le nombre d'heures enseignées pour une affectation existante.")
    @PutMapping("/{idAffectation}/{heure}/{annee}")
    public ResponseEntity<String> updateAffectation(
            @PathVariable Long idAffectation,
            @PathVariable int heure,
            @PathVariable Long annee
    ) {
        affectationService.updateAffectationHours(idAffectation, heure, annee);
        return ResponseEntity.ok("Affectation mise à jour avec succès");
    }

    @Operation(summary = "Supprimer une affectation", description = "Cette méthode permet de supprimer une affectation pour une année donnée.")
    @DeleteMapping("/{idAffectation}/{annee}")
    public ResponseEntity<String> deleteAffectation(@PathVariable Long idAffectation, @PathVariable Long annee) {
        affectationService.deleteAffectation(idAffectation, annee);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Récupérer toutes les affectations", description = "Cette méthode permet de récupérer la liste de toutes les affectations.")
    @GetMapping
    public ResponseEntity<List<AffectationDto>> getAllAffectations() {
        List<Affectation> affectations = affectationService.getAllAffectations();
        return new ResponseEntity<>(affectations.stream().map(AffectationMapper::toDto).toList(), HttpStatus.OK);
    }

    @Operation(summary = "Mettre à jour le commentaire d'une affectation", description = "Cette méthode permet de mettre à jour le commentaire d'une affectation existante.")
    @PutMapping("/{id}/{idAffectation}/commentaire")
    public CommentaireDto updateCommentaireAffectation(@PathVariable Long idAffectation, @RequestBody CommentaireDto commentaireDto, @CurrentSecurityContext(expression = "authentication?.name") String username) {
        enseignantService.updateCommentaireAffectation(idAffectation, username, commentaireDto.getCommentaire());
        return CommentaireDto.builder().commentaire(commentaireDto.getCommentaire()).build();
    }
}