package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.SemestreService;
import com.fst.il.m2.Projet.dto.SemestreDto;
import com.fst.il.m2.Projet.mapper.SemestreMapper;
import com.fst.il.m2.Projet.models.Niveau;
import com.fst.il.m2.Projet.models.Semestre;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/semestres")
@Tag(name = "Semestre Controller", description = "Gestion des semestres")
public class SemestreController {

    private final SemestreService semestreService;

    @Autowired
    public SemestreController(SemestreService semestreService) {
        this.semestreService = semestreService;
    }

    @Operation(summary = "Récupérer tous les semestres", description = "Cette méthode permet de récupérer la liste de tous les semestres.")
    @ApiResponse(responseCode = "200", description = "Liste des semestres récupérée")
    @GetMapping
    public ResponseEntity<List<SemestreDto>> getAllSemestres() {
        List<Semestre> semestres = semestreService.getAllSemestres();
        return new ResponseEntity<>(semestres.stream().map(SemestreMapper::toDto).toList(), HttpStatus.OK);
    }

    @Operation(summary = "Récupérer un semestre par ID", description = "Cette méthode permet de récupérer un semestre par son ID.")
    @ApiResponse(responseCode = "200", description = "Semestre récupéré avec succès")
    @ApiResponse(responseCode = "404", description = "Semestre non trouvé")
    @GetMapping("/id/{id}")
    public ResponseEntity<Semestre> getSemestreById(@PathVariable Long id) {
        try {
            Semestre semestre = semestreService.getSemestreById(id);
            return new ResponseEntity<>(semestre, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Récupérer les semestres par ID de niveau", description = "Cette méthode permet de récupérer les semestres par l'ID du niveau.")
    @ApiResponse(responseCode = "200", description = "Semestres récupérés avec succès")
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

    @Operation(summary = "Ajouter un nouveau semestre", description = "Cette méthode permet d'ajouter un nouveau semestre.")
    @ApiResponse(responseCode = "201", description = "Semestre ajouté avec succès")
    @PostMapping
    public ResponseEntity<SemestreDto> saveSemestre(@RequestBody SemestreDto semestreDto) {
        Semestre savedSemestre = semestreService.addSemestre(SemestreMapper.toEntity(semestreDto));
        return new ResponseEntity<>(SemestreMapper.toDto(savedSemestre), HttpStatus.CREATED);
    }

    @Operation(summary = "Supprimer un semestre par ID", description = "Cette méthode permet de supprimer un semestre par son ID.")
    @ApiResponse(responseCode = "204", description = "Semestre supprimé avec succès")
    @ApiResponse(responseCode = "406", description = "Semestre ne peut pas être supprimé")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSemestre(@PathVariable Long id) {
        if(!semestreService.hasModules(id)) {
            semestreService.deleteSemestre(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}