package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.FormationService;
import com.fst.il.m2.Projet.business.ResponsableFormationService;
import com.fst.il.m2.Projet.dto.FormationDto;
import com.fst.il.m2.Projet.mapper.FormationMapper;
import com.fst.il.m2.Projet.models.Formation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formations")
public class FormationController {

    private final FormationService formationService;
    private final ResponsableFormationService responsableFormationService;

    @Autowired
    public FormationController(FormationService formationService, ResponsableFormationService responsableFormationService) {
        this.formationService = formationService;
        this.responsableFormationService = responsableFormationService;
    }

    // Create a new Formation
    @PostMapping
    public ResponseEntity<Formation> createFormation(@RequestBody Formation formation) {
        Formation savedFormation = formationService.saveFormation(formation);
        return new ResponseEntity<>(savedFormation, HttpStatus.CREATED);
    }

    // Get all Formations
    @GetMapping
    public ResponseEntity<List<FormationDto>> getAllFormations() {
        List<Formation> formations = formationService.getAllFormations();
        List<FormationDto> formationDtos = formations.stream().map(new FormationMapper(responsableFormationService)::toDto).toList();

        return new ResponseEntity<>(formationDtos, HttpStatus.OK);
    }

    // Get Formation by ID
    @GetMapping("/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable Long id) {
        Formation formation = formationService.getFormationById(id);
        if (formation != null) {
            return new ResponseEntity<>(formation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update Formation
    @PutMapping("/{id}")
    public ResponseEntity<Formation> updateFormation(@PathVariable Long id, @RequestBody Formation formation) {
        try {
            Formation updatedFormation = formationService.updateFormation(id, formation);
            return new ResponseEntity<>(updatedFormation, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete Formation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        try {
            formationService.deleteFormation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
