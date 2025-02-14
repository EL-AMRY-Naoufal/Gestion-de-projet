package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.NiveauService;
import com.fst.il.m2.Projet.dto.NiveauDto;
import com.fst.il.m2.Projet.mapper.NiveauMapper;
import com.fst.il.m2.Projet.models.Formation;
import com.fst.il.m2.Projet.models.Niveau;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/niveaux")
public class NiveauController {

    private final NiveauService niveauService;

    @Autowired
    public NiveauController(NiveauService niveauService) {
        this.niveauService = niveauService;
    }

    // Get all Niveaux
    @GetMapping
    public ResponseEntity<List<NiveauDto>> getAllNiveaux() {
        List<Niveau> niveaux = niveauService.getAllNiveaux();
        return new ResponseEntity<>(niveaux.stream().map(NiveauMapper::toDto).toList(), HttpStatus.OK);
    }

    // Get a Niveau by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Niveau> getNiveauById(@PathVariable Long id) {
        try {
            Niveau niveau = niveauService.getNiveauById(id);
            return new ResponseEntity<>(niveau, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

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

    // Add a new Niveau
    @PostMapping
    public ResponseEntity<Niveau> saveNiveau(@RequestBody Niveau niveau) {
        Niveau savedNiveau = niveauService.saveNiveau(niveau);
        return new ResponseEntity<>(savedNiveau, HttpStatus.CREATED);
    }

    // Delete a Niveau by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNiveau(@PathVariable Long id) {
        try {
            niveauService.deleteNiveau(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
