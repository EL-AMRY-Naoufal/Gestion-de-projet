package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.AnneeService;
import com.fst.il.m2.Projet.dto.AnneeDto;
import com.fst.il.m2.Projet.models.Annee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/annees")
public class AnneeController {

    private final AnneeService anneeService;

    @Autowired
    public AnneeController(AnneeService anneeService) {
        this.anneeService = anneeService;
    }

    @PostMapping
    public ResponseEntity<Annee> createAnnee(@RequestBody Annee annee) {
        Annee savedAnnee = anneeService.saveAnnee(annee);
        return new ResponseEntity<>(savedAnnee, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AnneeDto>> getAllAnnees() {
        List<AnneeDto> annees = anneeService.getAllAnnees().stream().map(
                annee -> AnneeDto.builder().id(annee.getId()).label(annee.getDebut() + "-" + (annee.getDebut() + 1)).build()
        ).toList();
        return new ResponseEntity<>(annees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Annee> getAnneeById(@PathVariable Long id) {
        Annee annee = anneeService.getAnneeById(id);
        return new ResponseEntity<>(annee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnee(@PathVariable Long id) {
        anneeService.deleteAnnee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}