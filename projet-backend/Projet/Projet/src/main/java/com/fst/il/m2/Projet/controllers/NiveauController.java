package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.NiveauService;
import com.fst.il.m2.Projet.dto.NiveauDto;
import com.fst.il.m2.Projet.mapper.NiveauMapper;
import com.fst.il.m2.Projet.models.Formation;
import com.fst.il.m2.Projet.models.Niveau;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/niveaux")
@Tag(name = "Niveau Controller", description = "Gestion des niveaux")
public class NiveauController {

    private final NiveauService niveauService;

    @Autowired
    public NiveauController(NiveauService niveauService) {
        this.niveauService = niveauService;
    }

    @Operation(summary = "Récupérer tous les niveaux", description = "Cette méthode permet de récupérer la liste de tous les niveaux.")
    @ApiResponse(responseCode = "200", description = "Liste des niveaux récupérée")
    @GetMapping
    public ResponseEntity<List<NiveauDto>> getAllNiveaux() {
        List<Niveau> niveaux = niveauService.getAllNiveaux();
        return new ResponseEntity<>(niveaux.stream().map(NiveauMapper::toDto).toList(), HttpStatus.OK);
    }


    @Operation(summary = "Récupérer un niveau par ID", description = "Cette méthode permet de récupérer un niveau par son ID.")
    @ApiResponse(responseCode = "200", description = "Niveau récupéré avec succès")
    @ApiResponse(responseCode = "404", description = "Niveau non trouvé")    @GetMapping("/id/{id}")
    public ResponseEntity<Niveau> getNiveauById(@PathVariable Long id) {
        try {
            Niveau niveau = niveauService.getNiveauById(id);
            return new ResponseEntity<>(niveau, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Récupérer les niveaux par ID de formation", description = "Cette méthode permet de récupérer les niveaux par l'ID de la formation.")
    @ApiResponse(responseCode = "200", description = "Niveaux récupérés avec succès")
    @GetMapping("/formation/{formationId}")
    public List<Niveau> getNiveauxByFormationId(@PathVariable Long formationId) {
        List<Niveau> niveaux = niveauService.getNiveauxByFormation(Formation.builder().id(formationId).build());

        //emptying "orientations" and "formation" so the json is not too deep
        niveaux.forEach((n) -> {
            n.setFormation(null);
            n.setSemestres(null);
        });

        return niveaux;
    }

    @Operation(summary = "Ajouter un nouveau niveau", description = "Cette méthode permet d'ajouter un nouveau niveau.")
    @ApiResponse(responseCode = "201", description = "Niveau ajouté avec succès")
    @PostMapping
    public ResponseEntity<NiveauDto> saveNiveau(@RequestBody NiveauDto niveauDto) {
        Niveau savedNiveau = niveauService.saveNiveau(NiveauMapper.toEntity(niveauDto));
        return new ResponseEntity<>(NiveauMapper.toDto(savedNiveau), HttpStatus.CREATED);
    }

    @Operation(summary = "Supprimer un niveau par ID", description = "Cette méthode permet de supprimer un niveau par son ID.")
    @ApiResponse(responseCode = "204", description = "Niveau supprimé avec succès")
    @ApiResponse(responseCode = "406", description = "Niveau ne peut pas être supprimé")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNiveau(@PathVariable Long id) {
        if(!niveauService.hasSemestres(id)) {
            niveauService.deleteNiveau(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
