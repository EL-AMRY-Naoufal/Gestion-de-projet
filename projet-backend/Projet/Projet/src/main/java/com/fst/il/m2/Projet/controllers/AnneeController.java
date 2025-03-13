package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.AnneeService;
import com.fst.il.m2.Projet.business.DepartementService;
import com.fst.il.m2.Projet.dto.AnneeDto;
import com.fst.il.m2.Projet.mapper.AnneeMapper;
import com.fst.il.m2.Projet.mapper.DepartementMapper;
import com.fst.il.m2.Projet.models.Annee;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/annees")
@Tag(name = "Année Controller", description = "Gestion des années académiques")
public class AnneeController {

    private final AnneeService anneeService;
    private final DepartementService departementService;

    @Autowired
    public AnneeController(AnneeService anneeService, DepartementService departementService) {
        this.anneeService = anneeService;
        this.departementService = departementService;
    }

    @Operation(summary = "Créer une nouvelle année", description = "Cette méthode permet de créer une nouvelle année.")
    @PostMapping
    public ResponseEntity<AnneeDto> createAnnee(@RequestBody AnneeDto annee) {
        System.out.println("annee received : " + annee);
        Annee anneeToSave = AnneeMapper.toEntity(annee);
        AnneeDto savedAnneeDto = AnneeMapper.toDto(anneeService.saveAnnee(anneeToSave));
        return new ResponseEntity<>(savedAnneeDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer toutes les années", description = "Cette méthode permet de récupérer la liste de toutes les années.")
    @GetMapping
    public ResponseEntity<List<AnneeDto>> getAllAnnees() {
        List<AnneeDto> annees = anneeService.getAllAnnees().stream().map(
                annee -> AnneeDto.builder().id(annee.getId()).debut(annee.getDebut()).build()
        ).toList();
        return new ResponseEntity<>(annees, HttpStatus.OK);
    }

    @Operation(summary = "Récupérer une année par ID", description = "Cette méthode permet de récupérer une année par son ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Annee> getAnneeById(@PathVariable Long id) {
        Annee annee = anneeService.getAnneeById(id);
        return new ResponseEntity<>(annee, HttpStatus.OK);
    }

    @Operation(summary = "Supprimer une année", description = "Cette méthode permet de supprimer une année par son ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnee(@PathVariable Long id) {
        if (!anneeService.hasDepartements(id)) {
            anneeService.deleteAnnee(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}