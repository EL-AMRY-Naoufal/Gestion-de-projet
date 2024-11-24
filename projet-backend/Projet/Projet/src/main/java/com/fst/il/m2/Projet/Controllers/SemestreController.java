package com.fst.il.m2.Projet.Controllers;

import com.fst.il.m2.Projet.models.Semestre;
import com.fst.il.m2.Projet.business.SemestreService;
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
