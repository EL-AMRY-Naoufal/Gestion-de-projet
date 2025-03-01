package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.SemestreService;
import com.fst.il.m2.Projet.dto.SemestreDto;
import com.fst.il.m2.Projet.mapper.SemestreMapper;
import com.fst.il.m2.Projet.models.Niveau;
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
    public ResponseEntity<List<SemestreDto>> getAllSemestres() {
        List<Semestre> semestres = semestreService.getAllSemestres();
        return new ResponseEntity<>(semestres.stream().map(SemestreMapper::toDto).toList(), HttpStatus.OK);
    }

    // Get a Semestre by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Semestre> getSemestreById(@PathVariable Long id) {
        try {
            Semestre semestre = semestreService.getSemestreById(id);
            return new ResponseEntity<>(semestre, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/niveau/{niveauId}")
    public List<Semestre> getSemestresByNiveau(@PathVariable Long niveauId) {
        List<Semestre> semestres = semestreService.getSemestresByNiveau(Niveau.builder().id(niveauId).build());

        //emptying "orientation" so the json is not too deep
        semestres.forEach((s) -> {
            s.setNiveau(null);
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
    public ResponseEntity<SemestreDto> saveSemestre(@RequestBody SemestreDto semestreDto) {
        Semestre savedSemestre = semestreService.addSemestre(SemestreMapper.toEntity(semestreDto));
        return new ResponseEntity<>(SemestreMapper.toDto(savedSemestre), HttpStatus.CREATED);
    }

    // Delete a Semestre by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSemestre(@PathVariable Long id) {
        if(!semestreService.hasModules(id)) {
            semestreService.deleteSemestre(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}