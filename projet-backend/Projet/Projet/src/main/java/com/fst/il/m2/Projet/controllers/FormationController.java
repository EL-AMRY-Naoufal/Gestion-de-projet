package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.FormationService;
import com.fst.il.m2.Projet.business.ResponsableFormationService;
import com.fst.il.m2.Projet.dto.FormationDto;
import com.fst.il.m2.Projet.mapper.FormationMapper;
import com.fst.il.m2.Projet.models.Formation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/formations")
@Tag(name = "Formation Controller", description = "Gestion des formations")
public class FormationController {

    private final FormationService formationService;
    private final FormationMapper formationMapper;

    public FormationController(FormationService formationService, ResponsableFormationService responsableFormationService) {
        this.formationService = formationService;
        this.formationMapper = new FormationMapper(responsableFormationService);
    }

    @Operation(
            summary = "Créer une nouvelle formation",
            description = "Cette méthode permet de créer une nouvelle formation."
    )
    @ApiResponse(responseCode = "201", description = "Formation créée avec succès")
    @PostMapping
    public ResponseEntity<FormationDto> createFormation(@RequestBody FormationDto formationDto) {
        Formation savedFormation = formationService.saveFormation(this.formationMapper.toEntity(formationDto));
        return new ResponseEntity<>(this.formationMapper.toDto(savedFormation), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Récupérer toutes les formations",
            description = "Cette méthode permet de récupérer la liste de toutes les formations."
    )
    @ApiResponse(responseCode = "200", description = "Liste des formations récupérée")
    @GetMapping
    public ResponseEntity<List<FormationDto>> getAllFormations() {
        List<Formation> formations = formationService.getAllFormations();
        List<FormationDto> formationDtos = formations.stream().map(this.formationMapper::toDto).toList();

        return new ResponseEntity<>(formationDtos, HttpStatus.OK);
    }

    @Operation(
            summary = "Récupérer une formation par ID",
            description = "Cette méthode permet de récupérer une formation par son ID."
    )
    @ApiResponse(responseCode = "200", description = "Formation récupérée avec succès")
    @ApiResponse(responseCode = "404", description = "Formation non trouvée")
    @GetMapping("/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable Long id) {
        Formation formation = formationService.getFormationById(id);
        if (formation != null) {
            return new ResponseEntity<>(formation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Mettre à jour une formation",
            description = "Cette méthode permet de mettre à jour une formation existante."
    )
    @ApiResponse(responseCode = "200", description = "Formation mise à jour avec succès")
    @ApiResponse(responseCode = "404", description = "Formation non trouvée")
    @PutMapping("/{id}")
    public ResponseEntity<Formation> updateFormation(@PathVariable Long id, @RequestBody Formation formation) {
        try {
            Formation updatedFormation = formationService.updateFormation(id, formation);
            return new ResponseEntity<>(updatedFormation, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Supprimer une formation",
            description = "Cette méthode permet de supprimer une formation par son ID."
    )
    @ApiResponse(responseCode = "204", description = "Formation supprimée avec succès")
    @ApiResponse(responseCode = "406", description = "Formation ne peut pas être supprimée")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        if (!formationService.hasNiveaux(id)) {
            formationService.deleteFormation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
