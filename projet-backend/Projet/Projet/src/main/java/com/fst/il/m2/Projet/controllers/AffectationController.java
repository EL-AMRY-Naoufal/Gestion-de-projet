package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.AffectationService;
import com.fst.il.m2.Projet.dto.AffectationDto;
import com.fst.il.m2.Projet.mapper.AffectationMapper;
import com.fst.il.m2.Projet.models.Affectation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/affectations")
public class AffectationController {

    private final AffectationService affectationService;

    @Autowired
    public AffectationController(AffectationService affectationService) {
        this.affectationService = affectationService;
    }

    @PostMapping
    public ResponseEntity<AffectationDto> saveAffectation(@RequestBody AffectationDto affectationDto) {
        Affectation savedAffectation = affectationService.saveAffectation(AffectationMapper.toEntity(affectationDto));
        return new ResponseEntity<>(AffectationMapper.toDto(savedAffectation), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AffectationDto>> getAllAffectations() {
        List<Affectation> affectations = affectationService.getAllAffectations();
        return new ResponseEntity<>(affectations.stream().map(AffectationMapper::toDto).toList(), HttpStatus.OK);
    }
}
