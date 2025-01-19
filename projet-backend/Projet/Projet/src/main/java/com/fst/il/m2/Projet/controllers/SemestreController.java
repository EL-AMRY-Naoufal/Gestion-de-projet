package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.SemestreService;
import com.fst.il.m2.Projet.models.Orientation;
import com.fst.il.m2.Projet.models.Semestre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/semestres")
public class SemestreController {

    private final SemestreService semestreService;

    @Autowired
    public SemestreController(SemestreService semestreService) {
        this.semestreService = semestreService;
    }

    // Get all Semestres
    @GetMapping
    public ResponseEntity<List<Semestre>> getAllSemestres() {
        List<Semestre> semestres = semestreService.getAllSemestres();
        return new ResponseEntity<>(semestres, HttpStatus.OK);
    }

    // Get a Semestre by ID
    @GetMapping("/{id}")
    public ResponseEntity<Semestre> getSemestreById(@PathVariable Long id) {
        try {
            Semestre semestre = semestreService.getSemestreById(id);
            return new ResponseEntity<>(semestre, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/orientation/{orientationId}")
    public List<Semestre> getSemestresByOrientationId(@PathVariable Long orientationId) {
        List<Semestre> semestres = semestreService.getSemestresByOrientation(Orientation.builder().id(orientationId).build());

        //emptying "orientation" so the json is not too deep
        semestres.forEach((s) -> {
            s.setOrientation(null);
            //emptying "groupes" and "semestre" so the json is not too deep
            s.getModules().forEach((m) -> {
                m.setSemestre(null);
                m.setGroupes(null);
            });
        });

        return semestres;
    }

    // Add a new Semestre
    @PostMapping
    public ResponseEntity<Semestre> addSemestre(@RequestBody Semestre semestre) {
        Semestre savedSemestre = semestreService.addSemestre(semestre);
        return new ResponseEntity<>(savedSemestre, HttpStatus.CREATED);
    }

    // Delete a Semestre by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSemestre(@PathVariable Long id) {
        try {
            semestreService.deleteSemestre(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
