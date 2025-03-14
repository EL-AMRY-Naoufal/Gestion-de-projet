package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.AnneeService;
import com.fst.il.m2.Projet.business.DepartementService;
import com.fst.il.m2.Projet.business.ResponsableDepartementService;
import com.fst.il.m2.Projet.dto.DepartementDto;
import com.fst.il.m2.Projet.mapper.DepartementMapper;
import com.fst.il.m2.Projet.models.Departement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departements")
@RequiredArgsConstructor
@Tag(name = "Département Controller", description = "Gestion des départements")
public class DepartementController {

    private final DepartementService departementService;
    private final ResponsableDepartementService responsableDepartementService;
    private final AnneeService anneeService;

    @Operation(summary = "Enregistrer un nouveau département", description = "Cette méthode permet d'enregistrer un nouveau département.")
    @PostMapping
    public ResponseEntity<DepartementDto> saveDepartement(@RequestBody DepartementDto departementDto) {
        DepartementMapper departementMapper = new DepartementMapper(responsableDepartementService);
        System.out.println("departement received : " + departementDto);
        Departement departementToSave = departementMapper.toEntity(departementDto);
        System.out.println(departementDto.getResponsableDepartementId());
        DepartementDto savedDepartementDto = departementMapper.toDto(departementService.saveDepartement(departementToSave));
        return new ResponseEntity<>(savedDepartementDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer tous les départements", description = "Cette méthode permet de récupérer la liste de tous les départements.")
    @GetMapping
    public List<DepartementDto> getDepartements() {
        List<Departement> departements = departementService.getAllDepartements();
        return departements.stream().map(new DepartementMapper(responsableDepartementService)::toDto).toList();
    }

    @Operation(summary = "Récupérer les départements par année", description = "Cette méthode permet de récupérer la liste des départements pour une année donnée.")
    @GetMapping("/year/{anneeId}")
    public List<DepartementDto> getDepartementsByYear(@PathVariable Long anneeId) {
        List<Departement> departements = departementService.getDepartementsByAnnee(anneeService.getAnneeById(anneeId));
        return departements.stream().map(new DepartementMapper(responsableDepartementService)::toDto).toList();
    }

    @Operation(summary = "Récupérer un département par ID", description = "Cette méthode permet de récupérer un département par son ID.")
    @GetMapping("/id/{id}")
    public Departement getDepartementById(@PathVariable Long id) {
        return departementService.getDepartementById(id);
    }

    @Operation(summary = "Supprimer un département", description = "Cette méthode permet de supprimer un département par son ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartement(@PathVariable Long id) {
        if (!departementService.hasFormations(id)) {
            departementService.deleteDepartement(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}